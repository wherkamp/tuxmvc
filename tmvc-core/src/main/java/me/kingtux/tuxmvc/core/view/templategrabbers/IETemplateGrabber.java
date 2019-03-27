package me.kingtux.tuxmvc.core.view.templategrabbers;

import me.kingtux.tuxmvc.core.view.TemplateGrabber;
import me.kingtux.tuxutils.core.CommonUtils;
import me.kingtux.tuxutils.core.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Basically how this one works. When you request a template file  it grabs it. If the file exists it uses that. If not it uses that one inside the jar.
 */
public class IETemplateGrabber implements TemplateGrabber {
    private String location;
    private String extension;


    public IETemplateGrabber(String location, String extension) {
        this.location = location;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String s) {
        location = s;
    }


    public void setExtension(String extension) {
        this.extension = extension;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public String getFile(final String s) {
        if (s == null) return null;
        try {
            File externalTemplate = new File(new File(location), s.replace("/", File.separator) + extension);
            if (externalTemplate.exists()) {
                return FileUtils.fileToString(externalTemplate);
            } else {
                try {
                    InputStream is = getClass().getResourceAsStream("/" + location + "/" + s + extension);
                    return CommonUtils.bufferedReaderToString(new BufferedReader(new InputStreamReader(is)));
                } catch (Exception e) {
                    return null;
                }
            }
        } catch (NullPointerException ignored) {

        }
        return null;
    }
}
