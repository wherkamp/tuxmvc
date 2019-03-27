package me.kingtux.tuxmvc.core.view.templategrabbers;

import me.kingtux.tuxmvc.core.view.TemplateGrabber;
import me.kingtux.tuxutils.core.FileUtils;

import java.io.File;

public class ExternalTemplateGrabber implements TemplateGrabber {
    private String location;
    private String extension;


    public ExternalTemplateGrabber(String location, String extension) {
        this.location = location;
        this.extension = extension;
    }


    @Override
    public String getFile(String s) {
        File templateFile = new File(new File(location), s.replace("/", File.separator) + extension);
        if (templateFile.exists()) {
            return FileUtils.fileToString(templateFile);
        }
        return null;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public void setExtension(String s) {
        extension = s;
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
