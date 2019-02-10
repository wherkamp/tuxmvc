package me.kingtux.tmvc.core.errorhandler;

import me.kingtux.tmvc.core.Website;
import me.kingtux.tmvc.core.controller.ControllerExeception;
import me.kingtux.tmvc.core.controller.ControllerExecutor;
import me.kingtux.tmvc.core.errorhandler.annotations.EHPath;
import me.kingtux.tmvc.core.request.Request;
import me.kingtux.tmvc.core.view.View;
import me.kingtux.tmvc.core.view.ViewManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ErrorController {
    private Method method;
    private Object cl;

    public ErrorController(Object c, Method method) {
        this.method = method;
        cl = c;
    }

    public int status() {
        return method.getAnnotation(EHPath.class).status();
    }

    public String template() {
        return method.getAnnotation(EHPath.class).template();
    }

    @SuppressWarnings("All")
    public ControllerExecutor buildExecutor(Request request, ViewManager vb, Website website) {
        return () -> {
            request.setResponseHeader("Access-Control-Allow-Origin", website.getCORS());
            View view = vb.buildView(website, request);
            view.setTemplate(template());
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

    public String getName() {
        return method.getName();
    }
}
