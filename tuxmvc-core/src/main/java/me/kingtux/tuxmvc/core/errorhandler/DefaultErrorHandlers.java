package me.kingtux.tuxmvc.core.errorhandler;

import me.kingtux.tuxmvc.core.errorhandler.annotations.EHController;
import me.kingtux.tuxmvc.core.request.Request;

public abstract class DefaultErrorHandlers {
    @EHController(status = 404)
    public void four0four(Request request) {
        respond("404", request);
    }

    protected abstract void respond(String s, Request request);


}
