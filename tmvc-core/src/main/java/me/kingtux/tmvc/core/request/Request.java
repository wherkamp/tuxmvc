package me.kingtux.tmvc.core.request;

import me.kingtux.tmvc.core.annotations.RequestParam;
import me.kingtux.tmvc.core.annotations.RequestParam.Type;
import me.kingtux.tmvc.core.view.View;
import me.kingtux.tmvc.core.view.ViewManager;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A request contains all the things needed when requesting.
 */
public interface Request {

    String responseType();

    void responseType(String s);

    /**
     * Sets the response type of the response;
     *
     * @param s the response type
     */
    default void responseType(MimeType s) {
        responseType(s.getMimeType());
    }


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


    String cookie(String key);

    void cookie(String key, String value);

    void cookie(String key, String value, int maxAge);

    void removeCookie(String key);

    void clearCookie();


    RequestType getRequestType();


    //Header Crap
    Map<String, String> header();

    default String header(String s) {
        return header().get(s);
    }

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
                if (requestParam.type() == Type.GET) {
                    o.add(i, queryParam(requestParam.key(), requestParam.defaultValue()));

                } else if (requestParam.type() == Type.REQUEST) {
                    o.add(i, formParam(requestParam.key(), requestParam.defaultValue()));

                } else if (requestParam.type() == Type.URL) {
                    o.add(i, pathParam(requestParam.key(), requestParam.defaultValue()));

                } else {
                    o.add(i, requestParam.defaultValue());
                }
            } else if (type.isAssignableFrom(View.class)) {
                o.add(i, view);
            } else if (type.isAssignableFrom(Request.class)) {
                o.add(i, this);
            } else if (requestParam != null && requestParam.type() == Type.FILE) {
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
        if (parameters.length != o.size()) {
            throw new IllegalArgumentException("The sizes dont match");
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

    void respond(String s);

    /**
     * Has a respond happened
     *
     * @return the respond status
     */
    boolean hasResponded();

    List<UploadedFile> getUploadedFiles(String s);

    default UploadedFile getFirstUploadedFile(String s) {
        return getUploadedFiles(s).toArray(new UploadedFile[1])[0];
        // List<UploadedFile> files = getUploadedFiles(s);
        //return files.isEmpty() ? null : files.get(0);
    }


}
