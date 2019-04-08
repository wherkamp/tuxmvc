package me.kingtux.tuxmvc.core.rg;

/**
 * What is a template grabber you may ask?
 * Well it grabs the template file! and returns the file name
 *
 * @author KingTux
 */
public interface ResourceGrabber {

    String getFile(String s);



    String getLocation();

    void setLocation(String s);
}
