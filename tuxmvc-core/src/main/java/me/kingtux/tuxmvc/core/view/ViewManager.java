package me.kingtux.tuxmvc.core.view;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.request.Request;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;

public interface ViewManager {
    /**
     * @param tg The template grabber
     */
    void setResourceGrabber(ResourceGrabber tg);

    /**
     * This returns the template grabber
     *
     * @return
     */
    ResourceGrabber getResourceGrabber();

    /**
     * This takes the view and replaces the variables
     *
     * @param view the view to run
     * @return the String to return
     */
    default String parseView(View view) {
        return parseView(getResourceGrabber(), view);
    }

    String parseView(ResourceGrabber grabber, View view);

    /**
     * This builds a view!
     *
     * @param website thewebsite
     * @param rq      the request
     * @return the view
     */
    View buildView(Website website, Request rq);

    default View buildView(Website website) {
       return buildView(website, null);
    }

    /**
     * A Default template Variable takes is put into every view that is rendered
     * @param key the key
     * @param value the value
     */
    void registerDefaultViewVariable(String key, Object value);

    void registerViewVariableGrabber(String key, ViewVariableGrabber value);

    String getExtension();
}
