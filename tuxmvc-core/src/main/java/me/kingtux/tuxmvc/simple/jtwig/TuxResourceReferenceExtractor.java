package me.kingtux.tuxmvc.simple.jtwig;

import me.kingtux.tuxmvc.simple.impl.SimpleViewManager;
import org.apache.commons.compress.utils.IOUtils;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.ResourceReferenceExtractor;

import java.io.IOException;

public class TuxResourceReferenceExtractor implements ResourceReferenceExtractor {
    private SimpleViewManager manager;

    public TuxResourceReferenceExtractor(SimpleViewManager manager) {
        this.manager = manager;
    }

    @Override
    public ResourceReference extract(String s) {
        try {
            return ResourceReference.inline(new String(IOUtils.toByteArray(manager.getResourceGrabber().getFile(s + ".html").openStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
