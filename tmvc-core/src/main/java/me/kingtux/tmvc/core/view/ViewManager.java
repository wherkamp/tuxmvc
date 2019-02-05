package me.kingtux.tmvc.core.view;

import me.kingtux.tmvc.core.Website;
import me.kingtux.tmvc.core.request.Request;

public interface ViewManager {
    /**
     * @param tg The template grabber
     */
    void setTemplateGrabber(TemplateGrabber tg);

    /**
     * This returns the template grabber
     *
     * @return
     */
    TemplateGrabber getTemplateGrabber();

    /**
     * This takes the view and replaces the variables
     *
     * @param view the view to run
     * @return the String to return
     */
    String parseView(View view);

    /**
     * This builds a view!
     *
     * @param website thewebsite
     * @param rq      the request
     * @return the view
     */
    View buildView(Website website, Request rq);
}
