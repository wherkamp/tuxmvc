package me.kingtux.tuxmvc.simple.impl;


import io.javalin.Javalin;
import io.javalin.core.HandlerType;
import me.kingtux.simpleannotation.MethodFinder;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.annotations.Path;
import me.kingtux.tuxmvc.core.controller.SingleController;
import me.kingtux.tuxmvc.core.emails.EmailBuilder;
import me.kingtux.tuxmvc.core.emails.EmailSettings;
import me.kingtux.tuxmvc.core.errorhandler.ErrorController;
import me.kingtux.tuxmvc.core.errorhandler.annotations.EHPath;
import me.kingtux.tuxmvc.core.model.DatabaseController;
import me.kingtux.tuxmvc.core.request.RequestType;
import me.kingtux.tuxmvc.core.view.TemplateGrabber;
import me.kingtux.tuxmvc.core.view.ViewManager;

import java.lang.reflect.Method;


public class SimpleWebsite implements Website {
    private Javalin javalin;
    private ViewManager viewManager;
    private DatabaseController databaseController;
    private boolean https = false;
    private String cors = "*";
    private EmailSettings emailSettings;
    private WebsiteRules websiteRules;

    public SimpleWebsite(TemplateGrabber templateGrabber, Javalin javalin, String host, boolean https) {
        this.https = https;
        this.javalin = javalin;
        websiteRules = new SimpleWebsiteRules(https ? "https" : "http", host);
        viewManager = new SimpleViewManager(templateGrabber, this);

    }

    public void registerController(Object controller) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(controller.getClass(), Path.class)) {
            SingleController sc = new SingleController(controller, method);
            javalin.addHandler(getHandlerType(sc.getRequestType()), sc.getPath(), new ControllerHandler(sc, this    )::execute);
        }
    }

    @Override
    public void registerErrorHandler(Object errorHandler) {
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(errorHandler.getClass(), EHPath.class)) {
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
    public EmailBuilder buildEmailBuilder() {
        return null;
    }

    @Override
    public EmailBuilder buildEmtpyEmailBuilder() {
        return null;
    }

    @Override
    public EmailSettings getEmailSettings() {
        return emailSettings;
    }

    public DatabaseController getDatabaseController() {
        return null;
    }

    @Override
    public boolean isHttps() {
        return https;
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
}
