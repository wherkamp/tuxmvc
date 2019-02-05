package me.kingtux.tmvc.core.controller;
@FunctionalInterface
public interface ControllerExecutor {
    void execute() throws ControllerExeception;
}
