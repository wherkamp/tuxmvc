package me.kingtux.tuxmvc.simple.jtwig;

import me.kingtux.tuxmvc.simple.impl.SimpleViewManager;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.ResourceReferenceExtractor;

public class TuxResourceReferenceExtractor implements ResourceReferenceExtractor {
    private SimpleViewManager manager;

    public TuxResourceReferenceExtractor(SimpleViewManager manager) {
        this.manager = manager;
    }

    @Override
    public ResourceReference extract(String s) {
        return ResourceReference.inline(manager.getResourceGrabber().getFile(s));
    }


}
