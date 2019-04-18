package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.core.rg.Resource;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Basically how this one works. When you request a template file  it grabs it. If the file exists it uses that. If not it uses that one inside the jar.
 */
public class IEResourceGrabber implements ResourceGrabber {
    private String location;


    public IEResourceGrabber(String location) {
        this.location = location;
    }



    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String s) {
        location = s;
    }

    @Override
    public Resource getResource(String s) {
        if (s == null) return null;
        Resource resource = null;
        try {
            File externalTemplate = new File(new File(location), s.replace("/", File.separator));
            if (externalTemplate.exists()) {
                resource = new Resource(IOUtils.toByteArray(new FileInputStream(externalTemplate)), externalTemplate.toURI().toURL(), FilenameUtils.getExtension(externalTemplate.getAbsolutePath()));
            } else {
                URL url = getClass().getResource("/" + location + "/" + s);
                if (url == null) return null;
                resource = new Resource(IOUtils.toByteArray(getClass().getResourceAsStream("/" + location + "/" + s)), url, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
