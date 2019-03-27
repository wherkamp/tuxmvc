package me.kingtux.tuxmvc.core.view.templategrabbers;

import me.kingtux.tuxmvc.core.view.TemplateGrabber;
import me.kingtux.tuxutils.core.CommonUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This template grabber only grabs the internal one. The one inside the jar file;
 */
public class InternalTemplateGrabber  implements TemplateGrabber {
    private String location;
    private String extension;

    public InternalTemplateGrabber(String location, String extension) {
        this.location = location;
        this.extension = extension;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String getFile(String s) {
        try {
            InputStream is = getClass().getResourceAsStream("/"+location + "/" + s + extension);
            return CommonUtils.bufferedReaderToString(new BufferedReader(new InputStreamReader(is)));
        } catch (Exception e) {
            return null;
        }
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
