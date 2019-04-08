package me.kingtux.tuxmvc.core;

import me.kingtux.tuxmvc.core.emails.EmailManager;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.core.view.ViewManager;
import me.kingtux.tuxmvc.core.ws.WSHandler;

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

    void registerWebsocketHandler(WSHandler eh);



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
     */

    EmailManager getEmailManager();

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
     DatabaseManager getDBManager();
}
