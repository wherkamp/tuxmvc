package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxutils.core.CommonUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This template grabber only grabs the internal one. The one inside the jar file;
 */
public class InternalResourceGrabber implements ResourceGrabber {
    private String location;

    public InternalResourceGrabber(String location) {
        this.location = location;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public URL getFile(String s) {
        try {
           return  InternalResourceGrabber.class.getResource("/"+location + "/" + s );
        } catch (Exception e) {
            return null;
        }
    }



    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String s) {
        location = s;
    }
}
