package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxutils.core.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ExternalResourceGrabber implements ResourceGrabber {
    private String location;


    public ExternalResourceGrabber(String location) {
        this.location = location;
    }


    @Override
    public URL getFile(String s) {
        File templateFile = new File(new File(location), s.replace("/", File.separator));
        if (templateFile.exists()) {
            try {
                return templateFile.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
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
