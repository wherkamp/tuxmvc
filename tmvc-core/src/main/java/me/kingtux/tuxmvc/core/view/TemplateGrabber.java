package me.kingtux.tuxmvc.core.view;

/**
 * What is a template grabber you may ask?
 * Well it grabs the template file! and returns the file name
 *
 * @author KingTux
 */
public interface TemplateGrabber {

    String getFile(String s);


    void setExtension(String s);

    String getExtension();

    String getLocation();

    void setLocation(String s);
}
