package me.kingtux.tuxmvc.core.view;

import me.kingtux.tuxmvc.core.request.Request;

@FunctionalInterface
public interface ViewVariableGrabber {
    Object get(Request request);
}
