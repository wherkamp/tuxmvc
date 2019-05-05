package me.kingtux.tuxmvc.core;

import me.kingtux.tuxmvc.core.controller.SingleSitemapHandler;
import me.kingtux.tuxmvc.core.emails.EmailManager;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.core.view.ViewManager;
import me.kingtux.tuxmvc.core.ws.WSHandler;

import java.util.List;
import java.util.Properties;

@SuppressWarnings("ALL")
public interface Website {
    default void register(Object o) {
        registerController(o);
        registerErrorHandler(o);
        reigsterSiteMapHandler(o);
    }
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

    void reigsterSiteMapHandler(Object o);

    List<SingleSitemapHandler> getSSHs();
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


    Environment getEnvironment();
    String getCORS();
     DatabaseManager getDBManager();

    Properties getInternalProperties();

    ErrorMessageProvider getErrorMessageProvider();

    void setErrorMessageProvider(ErrorMessageProvider errorMessageProvider);
}
