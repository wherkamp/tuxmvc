package me.kingtux.tmvc.core.view.templategrabbers;

import me.kingtux.tmvc.core.view.TemplateGrabber;
import me.kingtux.tuxutils.core.CommonUtils;
import me.kingtux.tuxutils.core.FileUtils;

import java.io.*;

/**
 * Basically how this one works. When you request a template file  it grabs it. If the file exists it uses that. If not it uses that one inside the jar.
 */
public class IETemplateGrabber implements TemplateGrabber {
    private File externalLocation;
    private String internalLocation;
    private String extension = ".html";

    /**
     * @param externalLocation the folder that contains all external templates
     * @param internalLocation the base location for internal templates
     */
    public IETemplateGrabber(File externalLocation, String internalLocation) {
        this.externalLocation = externalLocation;
        this.internalLocation = internalLocation;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    @SuppressWarnings("Duplicates")
    @Override
    public String getFile(final String s) {
        File externalTemplate = new File(externalLocation, s.replace("/", File.separator) + extension);
        if (externalTemplate.exists()) {
            return FileUtils.fileToString(externalTemplate);
        } else {
            try {
                InputStream is = getClass().getResourceAsStream(internalLocation + File.separator + s + extension);
                return CommonUtils.bufferedReaderToString(new BufferedReader(new InputStreamReader(is)));
            } catch (Exception e) {
                return "";
            }
        }
    }
}
