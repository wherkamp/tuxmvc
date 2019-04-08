package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.errorhandler.DefaultErrorHandlers;
import me.kingtux.tuxmvc.core.request.Request;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxmvc.core.view.View;
import me.kingtux.tuxmvc.core.rg.templategrabbers.InternalResourceGrabber;

public class SimpleErrorHandler extends DefaultErrorHandlers {
    private ResourceGrabber grabber = new InternalResourceGrabber("tuxmvc/templates/eh");
    private Website site;

    public SimpleErrorHandler(Website site) {
        this.site = site;
    }

    @Override
    protected void respond(String s, Request request) {
        View view = site.getViewManager().buildView(site);
        view.setTemplate(s);
        request.respond(site.getViewManager().parseView(grabber, view));
    }
}
