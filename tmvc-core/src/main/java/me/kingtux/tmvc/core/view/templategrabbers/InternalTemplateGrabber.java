package me.kingtux.tmvc.core.view.templategrabbers;

import me.kingtux.tmvc.core.view.TemplateGrabber;
import me.kingtux.tuxutils.core.CommonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This template grabber only grabs the internal one. The one inside the jar file;
 */
public class InternalTemplateGrabber  implements TemplateGrabber {
    private String internalLocation;
    private String extension = ".html";

    public InternalTemplateGrabber(String internalLocation) {
        this.internalLocation = internalLocation;
    }

    public InternalTemplateGrabber(String internalLocation, String extension) {
        this.internalLocation = internalLocation;
        this.extension = extension;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String getFile(String s) {
        try {
            InputStream is = getClass().getResourceAsStream("/"+internalLocation + "/" + s + extension);
            return CommonUtils.bufferedReaderToString(new BufferedReader(new InputStreamReader(is)));
        } catch (Exception e) {
            return "";
        }
    }
}
