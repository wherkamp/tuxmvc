package me.kingtux.tmvc.core.view;

import me.kingtux.tmvc.core.request.Request;

@FunctionalInterface
public interface ViewVariableGrabber {
    Object get(Request request);
}
