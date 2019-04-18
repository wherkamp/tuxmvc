package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.core.rg.Resource;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExternalResourceGrabber implements ResourceGrabber {
    private String location;


    public ExternalResourceGrabber(String location) {
        this.location = location;
    }


    @Override
    public Resource getResource(String s) {
        File templateFile = new File(new File(location), s.replace("/", File.separator));
        if (!templateFile.exists()) return null;
        try {
            return new Resource(IOUtils.toByteArray(new FileInputStream(templateFile)), templateFile.toURI().toURL(), FilenameUtils.getExtension(templateFile.getAbsolutePath()));
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
