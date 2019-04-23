package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.core.rg.Resource;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

/**
 * This template grabber only grabs the internal one. The one inside the jar file;
 */
public class InternalResourceGrabber implements ResourceGrabber {
    private String location;

    public InternalResourceGrabber(String location) {
        this.location = location;
    }


    @Override
    public Resource getResource(String s) {
        String path = "/" + location + "/" + s;
        URL url = getClass().getResource(path);
        if (url == null) return null;
        try {
            return new Resource(IOUtils.toByteArray(getClass().getResourceAsStream(path)), url, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
