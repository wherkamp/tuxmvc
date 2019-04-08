package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxutils.core.FileUtils;

import java.io.File;

public class ExternalResourceGrabber implements ResourceGrabber {
    private String location;


    public ExternalResourceGrabber(String location) {
        this.location = location;
    }


    @Override
    public String getFile(String s) {
        File templateFile = new File(new File(location), s.replace("/", File.separator));
        if (templateFile.exists()) {
            return FileUtils.fileToString(templateFile);
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
