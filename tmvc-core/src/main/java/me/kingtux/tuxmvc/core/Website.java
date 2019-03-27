package me.kingtux.tuxmvc.core;

import me.kingtux.tuxmvc.core.emails.EmailBuilder;
import me.kingtux.tuxmvc.core.emails.EmailSettings;
import me.kingtux.tuxmvc.core.model.DatabaseController;
import me.kingtux.tuxmvc.core.view.ViewManager;

@SuppressWarnings("ALL")
public interface Website {

    /**
     * Register a controller to your website
     * @param controller the controller to add
     */
    void registerController(Object controller);


    /**
     * @param eh
     */
    void registerErrorHandler(Object eh);

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

    /**
     * This sets all the CORS rule
     * @param value the value
     */
    void setCORS(String value);

    String getCORS();
}
