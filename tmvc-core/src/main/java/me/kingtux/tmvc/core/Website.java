package me.kingtux.tmvc.core;

import me.kingtux.tmvc.core.model.DatabaseController;
import me.kingtux.tmvc.core.view.ViewManager;

@SuppressWarnings("ALL")
public interface Website {

    /**
     * Register a controller to your website
     * @param controller the controller to add
     */
    void registerController(Object controller);

    /**
     * The view manager
     * @see ViewManager
     * @return the view manager
     */
    ViewManager getViewManager();

    /**
     * The Database Controller.
     *
     * @return database controller
     * @deprecated This isn't available yet
     */
    @Deprecated
    DatabaseController getDatabaseController();
}
