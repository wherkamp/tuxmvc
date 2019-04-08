package me.kingtux.tuxmvc.core.controller;

import me.kingtux.tuxmvc.core.errorhandler.ErrorController;

public class ControllerExeception extends Exception {
    private final Throwable cause;

    public ControllerExeception(SingleController tc, Throwable throwable) {
        super("Failed to execute " + tc.getName());
        cause = throwable;
    }

    public ControllerExeception(ErrorController tc, Throwable throwable) {
        super("Failed to execute " + tc.getName());
        cause = throwable;
    }



    public Throwable getCause() {
        return cause;
    }
}
