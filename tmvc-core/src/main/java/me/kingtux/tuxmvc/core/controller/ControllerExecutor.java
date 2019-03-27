package me.kingtux.tuxmvc.core.controller;
@FunctionalInterface
public interface ControllerExecutor {
    void execute() throws ControllerExeception;
}
