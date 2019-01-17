package me.kingtux.tmvc.core.view.templategrabbers;

import me.kingtux.tmvc.core.view.TemplateGrabber;
import me.kingtux.tuxutils.common.FileUtils;

import java.io.File;

public class ExternalTemplateGrabber implements TemplateGrabber {
    private File externalLocation;
    private String extension = ".html";

    public File getExternalLocation() {
        return externalLocation;
    }

    @Override
    public String getFile(String s) {
        File templateFile = new File(externalLocation, s.replace("/", File.separator) + extension);
        if (templateFile.exists()) {
            return FileUtils.fileToString(templateFile);
        }
        return "";
    }
}
