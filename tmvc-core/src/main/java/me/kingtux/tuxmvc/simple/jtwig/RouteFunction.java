package me.kingtux.tuxmvc.simple.jtwig;

import me.kingtux.tuxmvc.core.Website;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class RouteFunction extends SimpleJtwigFunction {
    private Website site;

    public RouteFunction(Website site) {
        this.site = site;
    }

    @Override
    public String name() {
        return "route";
    }

    @Override
    public Object execute(FunctionRequest functionRequest) {
        return site.route((String) functionRequest.get(0));
    }
}
