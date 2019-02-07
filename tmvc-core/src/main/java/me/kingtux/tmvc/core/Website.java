package me.kingtux.tmvc.core;

import me.kingtux.tmvc.core.emails.EmailBuilder;
import me.kingtux.tmvc.core.emails.EmailSettings;
import me.kingtux.tmvc.core.errorhandler.ErrorHandler;
import me.kingtux.tmvc.core.model.DatabaseController;
import me.kingtux.tmvc.core.view.ViewManager;

@SuppressWarnings("ALL")
public interface Website {

    /**
     * Register a controller to your website
     * @param controller the controller to add
     */
    void registerController(Object controller);


    void registerErrorHandler(ErrorHandler eh);
    /**
     * The view manager
     * @see ViewManager
     * @return the view manager
     */
    ViewManager getViewManager();

    /**
     * This one already provides the FROM
     *
     * @return the Email builder
     */
    EmailBuilder buildEmailBuilder();

    /**
     * This one is empty
     *
     * @return the EmailBuilder
     */
    EmailBuilder buildEmtpyEmailBuilder();

    /**
     * Gets the email settings
     *
     * @return settings
     */
    EmailSettings getEmailSettings();
    /**
     * The Database Controller.
     *
     * @return database controller
     * @deprecated This isn't available yet
     */
    @Deprecated
    DatabaseController getDatabaseController();

    /**
     * Https
     * @deprecated use getSiteRules#protocol()
     * @return is is running https
     */
    @Deprecated
    boolean isHttps();

    /**
     * Closes and shutdown the website
     */
    void close();


    /**
     * This will create a full on url.
     *
     * @param path the path after the protocol + host
     * @return the full url
     */
    default String route(String path) {
        return getSiteRules().baseURL() + "/" + path;
    }

    /**
     * The rules for the website
     *
     * @return the site rules
     */
    WebsiteRules getSiteRules();
}
