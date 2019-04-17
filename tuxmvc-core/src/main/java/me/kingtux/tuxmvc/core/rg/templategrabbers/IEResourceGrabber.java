package me.kingtux.tuxmvc.core.rg.templategrabbers;

import me.kingtux.tuxmvc.TuxMVC;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;

import java.io.File;
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



    @SuppressWarnings("Duplicates")
    @Override
    public URL getFile(final String s) {
        if (s == null) return null;
        try {
            File externalTemplate = new File(new File(location), s.replace("/", File.separator));
            TuxMVC.TUXMVC_LOGGER.debug(externalTemplate.getPath());
            if (externalTemplate.exists()) {
                return externalTemplate.toURI().toURL();
            } else {
                try {
                    URL url = getClass().getResource("/" + location + "/" + s);
                    if(url==null){
                        TuxMVC.TUXMVC_LOGGER.warn("NO INTERNAL RESOURCE "+ "/" + location + "/" + s);
                        return null;
                    }
                    return url;
                } catch (Exception e) {
                    return null;
                }
            }
        } catch (NullPointerException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
