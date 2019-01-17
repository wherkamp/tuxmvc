package me.kingtux.tmvc.core;

import me.kingtux.tmvc.core.model.DatabaseController;
import me.kingtux.tmvc.core.view.ViewManager;

public interface Website {
    void registerController(Controller controller);


    ViewManager getViewManager();

    DatabaseController getDatabaseController();
}
