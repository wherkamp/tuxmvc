package me.kingtux.tuxmvc.core.rg;

import java.net.URL;

/**
 * What is a template grabber you may ask?
 * Well it grabs the template file! and returns the file name
 *
 * @author KingTux
 */
public interface ResourceGrabber {

    URL getFile(String s);



    String getLocation();

    void setLocation(String s);
}
