package me.kingtux.tuxmvc.simple.impl;


import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxjsitemap.SiteMapGenerator;
import me.kingtux.tuxmvc.TuxMVC;
import me.kingtux.tuxmvc.core.Environment;
import me.kingtux.tuxmvc.core.ErrorMessageProvider;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.annotations.Controller;
import me.kingtux.tuxmvc.core.annotations.SitemapHandler;
import me.kingtux.tuxmvc.core.annotations.Websocket;
import me.kingtux.tuxmvc.core.controller.SingleController;
import me.kingtux.tuxmvc.core.controller.SingleSitemapHandler;
import me.kingtux.tuxmvc.core.emails.EmailManager;
import me.kingtux.tuxmvc.core.errorhandler.ErrorController;
import me.kingtux.tuxmvc.core.errorhandler.annotations.EHController;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.core.request.RequestType;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxmvc.core.rg.ResourceGrabbers;
import me.kingtux.tuxmvc.core.view.ViewManager;
import me.kingtux.tuxmvc.core.ws.WSHandler;
import me.kingtux.tuxmvc.simple.impl.email.SimpleEmailManager;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SimpleWebsite implements Website {
    private Javalin javalin;
    private ViewManager viewManager;
    private String cors = "*";
    private DatabaseManager dbManager;
    private EmailManager emailManager;
    private WebsiteRules websiteRules;
    public static Logger TUXMVC_LOGGER = LoggerFactory.getLogger(TuxMVC.class);
    private Properties internalProperties;
    private Environment environment;
    private List<SingleController> controllerList = new ArrayList<>();
    private List<SingleSitemapHandler> singleSitemapHandlers = new ArrayList<>();
    private ErrorMessageProvider errorMessageProvider;
    public SimpleWebsite(WebsiteRules websiteRules, int port, SimpleEmailManager.SEmailBuilder em, DatabaseManager dbManager, SslContextFactory sslContextFactory, int sslPort, Environment environment) {
        Javalin.log = TuxMVC.TUXMVC_LOGGER;
        this.websiteRules = websiteRules;
        this.environment = environment;
        loadInternalProperties();
        if (Boolean.parseBoolean(internalProperties.getProperty("sitemap.directory", "true"))) {
            new File("sitemap").mkdir();
        }
        if (Boolean.parseBoolean(internalProperties.getProperty("sitemap.auto", "true"))) {
            new File("sitemap").mkdir();
            new SiteMapAuto(this).start();
            internalProperties.setProperty("sitemap.auto","true");
        }
        initJavalin(sslContextFactory, port, sslPort);
        emailManager = em.setSite(this).build();
        this.dbManager = dbManager;
        registerErrorHandler(new SimpleErrorHandler(this));

        createViewManager();
        javalin.start(port);
        TuxMVC.TUXMVC_LOGGER.info(String.format("%s is ready! Go to %s", this.websiteRules.name(), this.websiteRules.baseURL()));
    }

    private void loadInternalProperties() {
    internalProperties = new Properties();
        try {
            internalProperties.load(SimpleWebsite.class.getResourceAsStream("/tuxmvc.properties"));
        } catch (IOException e) {
            TuxMVC.TUXMVC_LOGGER.error("Unable to find tuxmvc.properties in jar" ,e);
            System.exit(1);
        }
    }

    private void createViewManager() {
        ResourceGrabber resourceGrabber = ResourceGrabbers.valueOf(getInternalProperties().getProperty("template.grabber", "INTERNAL_EXTERNAL_GRABBER")).build(getInternalProperties().getProperty("template.path"));
        viewManager = new SimpleViewManager(resourceGrabber, this, getInternalProperties().getProperty("tempalte.extension"));
    }


    public void registerController(Object controller) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), Controller.class)) {
            SingleController sc = new SingleController(controller, method);
            controllerList.add(sc);
            TUXMVC_LOGGER.debug(sc.getPath() + " -> " + controller.getClass().getSimpleName() + "#" + method.getName());
            javalin.addHandler(getHandlerType(sc.getRequestType()), sc.getPath(), new ControllerHandler(sc, this)::execute);
        }
    }

    private void initJavalin(SslContextFactory sslContextFactory, int port, int sslPort) {
        javalin = Javalin.create(c -> {
            c.inner.resourceHandler = new TMResourceHandler(this);
            c.sessionHandler(SessionHandler::new);
            c.wsFactoryConfig(webSocketServletFactory -> {
                try {
                    webSocketServletFactory.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (sslContextFactory != null) {

                TUXMVC_LOGGER.debug("Using SSL Server!");
                c.server(() -> {
                    Server server = new Server();
                    ServerConnector sslConnector = new ServerConnector(server, sslContextFactory);
                    sslConnector.setPort(sslPort);
                    ServerConnector connector = new ServerConnector(server);
                    connector.setPort(port);
                    server.setConnectors(new Connector[]{sslConnector, connector});
                    return server;
                });

            } else {
                TUXMVC_LOGGER.debug("Starting Regular Website");
            }

        });
    }
    @Override
    public void registerErrorHandler(Object errorHandler) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(errorHandler.getClass(), EHController.class)) {
            ErrorController errorController = new ErrorController(errorHandler, method);
            TUXMVC_LOGGER.debug(errorController.status() + " -> " + errorController.getClass().getSimpleName() + "#" + method.getName());
            javalin.error(errorController.status(), new ErrorControllerHandler(errorController, this)::execute);
        }
    }

    @Override
    public void registerWebsocketHandler(WSHandler eh) {
        String path = eh.getClass().getAnnotation(Websocket.class).path();
        //TuxMVC.TUXMVC_LOGGER.warn("Websockets are in early development");
        TUXMVC_LOGGER.debug(path + " -> " + eh.getClass().getSimpleName());
        javalin.ws(path, ws -> {
            ws.onConnect(session -> eh.onConnect(new SimpleWSSession(session)));
            ws.onMessage(wsMessageContext -> eh.onMessage(new SimpleWSSession(wsMessageContext), wsMessageContext.message()));
            ws.onError(e -> eh.onError(new SimpleWSSession(e), e.error()));
            ws.onClose(c -> eh.onClose(new SimpleWSSession(c), c.status(), c.reason()));
        });

    }

    @Override
    public void reigsterSiteMapHandler(Object o) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(o.getClass(), SitemapHandler.class)) {
            SingleSitemapHandler sc = new SingleSitemapHandler(o, method);
            singleSitemapHandlers.add(sc);
        }
    }

    @Override
    public List<SingleSitemapHandler> getSSHs() {
        return singleSitemapHandlers;
    }

    private HandlerType getHandlerType(RequestType requestType) {
        switch (requestType) {
            case GET:
                return HandlerType.GET;
            case POST:
                return HandlerType.POST;
            case PUT:
                return HandlerType.PUT;
            case DELETE:
                return HandlerType.DELETE;
        }
        return HandlerType.GET;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }



    @Override
    public EmailManager getEmailManager() {
        return emailManager;
    }


    public void close() {
        javalin.stop();
    }

    @Override
    public WebsiteRules getSiteRules() {
        return websiteRules;
    }

    @Override
    public void setCORS(String s) {
        this.cors = s;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public String getCORS() {
        return cors;
    }

    @Override
    public DatabaseManager getDBManager() {
        return dbManager;
    }

    @Override
    public Properties getInternalProperties() {
        return internalProperties;
    }

    public Javalin getJavalin() {
        return javalin;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }

    void setWebsiteRules(WebsiteRules websiteRules) {
        this.websiteRules = websiteRules;
    }

    public List<SingleController> getControllerList() {
        return controllerList;
    }

    @Override
    public ErrorMessageProvider getErrorMessageProvider() {
        return errorMessageProvider;
    }

    public void setErrorMessageProvider(ErrorMessageProvider errorMessageProvider) {
        this.errorMessageProvider = errorMessageProvider;
    }
}
