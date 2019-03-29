package me.kingtux.tuxmvc.simple.impl;


import io.javalin.Javalin;
import io.javalin.core.HandlerType;
import io.javalin.staticfiles.Location;
import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.annotations.Controller;
import me.kingtux.tuxmvc.core.controller.SingleController;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.core.emails.EmailManager;
import me.kingtux.tuxmvc.core.errorhandler.ErrorController;
import me.kingtux.tuxmvc.core.errorhandler.annotations.EHController;
import me.kingtux.tuxmvc.core.request.RequestType;
import me.kingtux.tuxmvc.core.view.TemplateGrabber;
import me.kingtux.tuxmvc.core.view.ViewManager;
import me.kingtux.tuxmvc.simple.impl.email.SimpleEmailManager;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
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
    private Logger siteLogger = LoggerFactory.getLogger("TuxMVC");


    public SimpleWebsite(WebsiteRules websiteRules, int port, File file, TemplateGrabber tg, EmailManager em, DatabaseManager dbManager, SslContextFactory sslContextFactory, int sslPort) {
        this.websiteRules = websiteRules;
        emailManager = em;
        ((SimpleEmailManager) emailManager).setSite(this);
        this.dbManager = dbManager;

        Javalin javalin = Javalin.create().enableStaticFiles(file.getPath(), Location.EXTERNAL);
        if (!file.exists()) file.mkdir();
        if (sslContextFactory != null) {
            siteLogger.debug("Using SSL Server!");
            javalin.server(() -> {
                Server server = new Server();
                ServerConnector sslConnector = new ServerConnector(server, sslContextFactory);
                sslConnector.setPort(sslPort);
                ServerConnector connector = new ServerConnector(server);
                connector.setPort(port);
                server.setConnectors(new Connector[]{sslConnector, connector});
                return server;
            });
        } else {
            siteLogger.debug("Starting Regular Website");
            javalin.port(port);
        }
        this.javalin = javalin;
        javalin.start();
        viewManager = new SimpleViewManager(tg, this);
    }

    public void registerController(Object controller) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), Controller.class)) {
            SingleController sc = new SingleController(controller, method);
            javalin.addHandler(getHandlerType(sc.getRequestType()), sc.getPath(), new ControllerHandler(sc, this    )::execute);
        }
    }

    @Override
    public void registerErrorHandler(Object errorHandler) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(errorHandler.getClass(), EHController.class)) {
            ErrorController errorController = new ErrorController(errorHandler, method);
            javalin.error(errorController.status(), new ErrorControllerHandler(errorController, this)::execute);
        }
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
}
