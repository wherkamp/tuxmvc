package me.kingtux.tuxmvc.simple.impl;


import io.javalin.Javalin;
import io.javalin.core.HandlerType;
import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxmvc.TuxMVC;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.annotations.Controller;
import me.kingtux.tuxmvc.core.annotations.Websocket;
import me.kingtux.tuxmvc.core.controller.SingleController;
import me.kingtux.tuxmvc.core.emails.EmailManager;
import me.kingtux.tuxmvc.core.errorhandler.ErrorController;
import me.kingtux.tuxmvc.core.errorhandler.annotations.EHController;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.core.request.RequestType;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
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
import java.lang.reflect.Method;


public class SimpleWebsite implements Website {
    private Javalin javalin;
    private ViewManager viewManager;
    private String cors = "*";
    private DatabaseManager dbManager;
    private EmailManager emailManager;
    private WebsiteRules websiteRules;
    public static Logger TUXMVC_LOGGER = LoggerFactory.getLogger(TuxMVC.class);


    public SimpleWebsite(WebsiteRules websiteRules, int port, File file, ResourceGrabber tg, SimpleEmailManager.SEmailBuilder em, DatabaseManager dbManager, SslContextFactory sslContextFactory, int sslPort, String property) {
        this.websiteRules = websiteRules;

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

        }).start(port);

        emailManager = em.setSite(this).build();
        ((SimpleEmailManager) emailManager).setSite(this);
        this.dbManager = dbManager;
        if (!file.exists()) file.mkdir();

        viewManager = new SimpleViewManager(tg, this, property);
        registerErrorHandler(new SimpleErrorHandler(this));
        TuxMVC.TUXMVC_LOGGER.info(String.format("%s is ready! Go to %s", this.websiteRules.name(), this.websiteRules.baseURL()));
    }


    public void registerController(Object controller) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), Controller.class)) {
            SingleController sc = new SingleController(controller, method);
            TUXMVC_LOGGER.debug(sc.getPath() + " -> " + controller.getClass().getSimpleName() + "#" + method.getName());
            javalin.addHandler(getHandlerType(sc.getRequestType()), sc.getPath(), new ControllerHandler(sc, this)::execute);
        }
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
            ws.onError(e -> eh.onError(new SimpleWSSession(e), e.getError()));
            ws.onClose(c -> eh.onClose(new SimpleWSSession(c), c.getStatusCode(), c.getReason()));
        });

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
    public String getCORS() {
        return cors;
    }

    @Override
    public DatabaseManager getDBManager() {
        return dbManager;
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
}
