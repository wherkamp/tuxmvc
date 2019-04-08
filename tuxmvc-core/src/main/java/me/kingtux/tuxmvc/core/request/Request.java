package me.kingtux.tuxmvc.core.request;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.annotations.RequestParam;
import me.kingtux.tuxmvc.core.view.View;
import me.kingtux.tuxmvc.core.view.ViewManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A request contains all the things needed when requesting.
 */
public interface Request {
    /**
     * THe response type
     *
     * @return the response type
     */
    String responseType();

    /**
     * SEts the response type
     *
     * @param s the response type
     */
    void responseType(String s);

    /**
     * Sets the response type of the response;
     *
     * @param s the response type
     */
    default void responseType(MimeType s) {
        responseType(s.getMimeType());
    }

    /**
     * Gets the query string
     *
     * @return the query string
     */
    String queryString();

    /**
     * The query param map
     * @return a map of query params (GET)
     */
    Map<String, String> queryParam();

    default String queryParam(String key) {
        return queryParam(key, null);
    }

    default String queryParam(String key, String def) {
        return queryParam().getOrDefault(key, def);
    }


    Map<String, String> formParam();

    default String formParam(String key) {
        return formParam(key, null);
    }

    default String formParam(String key, String def) {
        return formParam().getOrDefault(key, def);
    }

    Map<String, String> cookieMap();

    default String cookie(String key) {
        return cookieMap().get(key);
    }

    void cookie(String key, String value);


    void cookie(String key, String value, int maxAge);

    void removeCookie(String key);

    void clearCookie();


    RequestType getRequestType();

    /**
     * Gets the status
     *
     * @return get the status
     */
    int status();

    /**
     * The status value
     * @param status value
     */
    void status(int status);

    /**
     * the status value
     * @param status the status
     */
    default void status(HTTPCode status) {
        status(status.getCode());
    }

    /**
     * Header map
     * @return the map
     */
    Map<String, String> header();

    /**
     * Gets a header value
     * @param s key
     * @return value
     */
    default String header(String s) {
        return header().get(s);
    }

    /**
     * The redirect
     * @param url the url to
     */
    default void redirect(String url) {
        redirect(url, HTTPCode.TEMP_REDIRECT);
    }

    /**
     * Tells the client to redirect
     *
     * @param url      url
     * @param httpCode http code
     */
    default void redirect(String url, HTTPCode httpCode) {
        redirect(url, httpCode.getCode());
    }

    /**
     * Tells the client to redirect
     *
     * @param url      url to go to
     * @param httpCode the http code
     */
    void redirect(String url, int httpCode);

    /**
     * Basically this will sort the paramters into its needed groups and order
     *
     * @param parameters the paramters from the method
     * @param view       THe View
     * @return the Object[] for the Method#invoke
     */
    default Object[] getArguments(Parameter[] parameters, View view) {
        List<Object> o = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = parameters[i].getType();
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);

            if (requestParam != null && type == String.class) {
                if (requestParam.type() == RequestParam.Type.GET) {
                    o.add(i, queryParam(requestParam.key(), requestParam.defaultValue()));

                } else if (requestParam.type() == RequestParam.Type.REQUEST) {
                    o.add(i, formParam(requestParam.key(), requestParam.defaultValue()));

                } else if (requestParam.type() == RequestParam.Type.URL) {
                    o.add(i, pathParam(requestParam.key(), requestParam.defaultValue()));
                }
            } else if (type.isAssignableFrom(View.class)) {
                o.add(i, view);
            } else if (type.isAssignableFrom(Request.class)) {
                o.add(i, this);
            } else if (requestParam != null && requestParam.type() == RequestParam.Type.FILE) {
                if (type.isAssignableFrom(List.class)) {
                    //Get all
                    o.add(i, getUploadedFiles(requestParam.key()));
                } else {
                    o.add(i, getFirstUploadedFile(requestParam.key()));
                }
            } else {
                o.add(i, null);
            }
        }
        return o.toArray();
    }

    /**
     * So if a controller is the following
     * /home/cows/:cow:
     * You can type in cow into this and it will return what that is set too
     *
     * @param key
     * @return
     */
    String pathParam(String key);

    default String pathParam(String key, String defaultValue) {
        if (pathParam(key) == null || pathParam(key).isEmpty()) return defaultValue;
        return pathParam(key);
    }

    String path();

    String[] pathSplit();

    int port();

    String ip();

    String url();

    /**
     * This responds by prsing the view and using the respond(String s)
     *
     * @param view the view to respond with
     * @param vm   the ViewManager
     */
    default void respond(View view, ViewManager vm) {
        respond(vm.parseView(view));
    }

    /**
     * Respond with a string
     * @param s the string to respond with
     */
    void respond(String s);

    /**
     * Respond with the InputStream
     *
     * @param stream the stream
     */
    void respond(InputStream stream);

    /**
     * Respond with a file!
     *
     * @param file the file
     */
    default void respond(File file) {
        try {
            respond(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Has a respond happened
     *
     * @return the respond status
     */
    boolean hasResponded();

    /**
     * <b>Default: Empty List</b>
     *
     * @param s
     * @return
     */
    List<UploadedFile> getUploadedFiles(String s);

    /**
     * Value would be null
     * <b>Default Null</b>
     *
     * @param s the key name
     * @return the UploadedFile
     */
    default UploadedFile getFirstUploadedFile(String s) {
        if (getUploadedFiles(s).size() == 0) return null;
        return getUploadedFiles(s).toArray(new UploadedFile[1])[0];
    }


    /**
     * sets a session value
     *
     * @param key   key
     * @param value value
     */
    void session(String key, Object value);

    /**
     * gets session value
     *
     * @param key the key
     * @param <T> the type
     * @return the value
     */
    default <T> T session(String key) {
        return (T) sessionMap().get(key);
    }

    /**
     * The session map
     * @return a map of the session entrys
     */
    Map<String, Object> sessionMap();

    /**
     * This just redirects to baseURL
     * <b>If you are at the base url. It will break</b>
     */
    default void returnToBase() {
        redirect(getWebsite().getSiteRules().baseURL());
    }

    /**
     * The website
     *
     * @return the website
     */
    Website getWebsite();

    void setResponseHeader(String key, String value);
}
