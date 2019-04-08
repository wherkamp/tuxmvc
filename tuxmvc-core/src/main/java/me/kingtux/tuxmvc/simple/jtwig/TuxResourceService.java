package me.kingtux.tuxmvc.simple.jtwig;

import me.kingtux.tuxmvc.simple.impl.SimpleViewManager;
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.ResourceReferenceExtractor;
import org.jtwig.resource.resolver.RelativeResourceResolver;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TuxResourceService extends ResourceService {
    private SimpleViewManager viewManager;

    public TuxResourceService(Map<String, ResourceLoader> loaderMap, List<TypedResourceLoader> loaderList, Collection<String> absoluteResourceTypes, Collection<RelativeResourceResolver> relativeResourceResolvers, ResourceReferenceExtractor resourceReferenceExtractor) {
        super(loaderMap, loaderList, absoluteResourceTypes, relativeResourceResolvers, resourceReferenceExtractor);
    }

    public TuxResourceService(SimpleViewManager viewManager) {
        super(null, null, null, null,null);
        this.viewManager = viewManager;
    }

    @Override
    public ResourceReference resolve(ResourceReference current, String path) {
        return new TuxResourceReferenceExtractor(viewManager).extract(path);
    }

    @Override
    public ResourceMetadata loadMetadata(ResourceReference reference) {
        return new TuxResourceMetaData(reference.getPath());
    }
}
