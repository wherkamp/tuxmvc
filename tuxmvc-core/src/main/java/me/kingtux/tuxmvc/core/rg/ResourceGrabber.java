package me.kingtux.tuxmvc.core.rg;

import java.net.URL;

/**
 * What is a template grabber you may ask?
 * Well it grabs the template file! and returns the file name
 *
 * @author KingTux
 */
public interface ResourceGrabber {

    @Deprecated
    default URL getFile(String s) {
        return getResource(s).getUrl();
    }

    /**
     * Returns a resource based on the path
     * @param s the path to the file.
     * @return The Resource null if not found
     */
    Resource getResource(String s);

    String getLocation();

    void setLocation(String s);
}
