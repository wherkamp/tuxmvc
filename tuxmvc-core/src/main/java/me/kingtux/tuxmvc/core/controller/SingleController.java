package me.kingtux.tuxmvc.core.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.annotations.Controller;
import me.kingtux.tuxmvc.core.request.Request;
import me.kingtux.tuxmvc.core.request.RequestType;
import me.kingtux.tuxmvc.core.view.View;
import me.kingtux.tuxmvc.core.view.ViewManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleController {
    private Method method;
    private Object cl;

    public SingleController(Object c, Method method) {
        this.method = method;
        cl = c;
    }

    public String getPath() {
        return method.getAnnotation(Controller.class).path();
    }
    public Boolean sitemap() {
        return method.getAnnotation(Controller.class).sitemap();
    }

    public RequestType getRequestType() {
        return method.getAnnotation(Controller.class).requestType();
    }

    public String getTemplate() {
        return method.getAnnotation(Controller.class).template();
    }

    @SuppressWarnings("All")
    public ControllerExecutor buildExecutor(Request request, ViewManager vb, Website website) {
        return () -> {
            request.setResponseHeader("Access-Control-Allow-Origin", website.getCORS());
            View view = vb.buildView(website, request);
            view.setTemplate(getTemplate());
            try {
                method.invoke(cl, request.getArguments(method.getParameters(), view));
            } catch (InvocationTargetException e) {
                throw new ControllerExeception(this, e.getCause());
            } catch (Throwable e) {
                throw new ControllerExeception(this, e);
            }
            if (!request.hasResponded() || !view.getTemplate().equals(""))
                request.respond(view, vb);
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SingleController)) return false;
        SingleController singleController = (SingleController) obj;
        return ((singleController.getPath().equals(getPath())) && singleController.getRequestType() == getRequestType());
    }

    public String getName() {
        return method.getName();
    }
}
