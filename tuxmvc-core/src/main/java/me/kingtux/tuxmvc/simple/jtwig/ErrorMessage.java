package me.kingtux.tuxmvc.simple.jtwig;

import jdk.nashorn.internal.runtime.Undefined;
import me.kingtux.tuxmvc.core.Website;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

public class ErrorMessage extends SimpleJtwigFunction {
    private Website site;

    public ErrorMessage(Website site) {
        this.site = site;
    }

    @Override
    public String name() {
        return "error_message";
    }

    @Override
    public Object execute(FunctionRequest functionRequest) {
        if (functionRequest.get(0) instanceof Undefined) return null;
        if (site.getErrorMessageProvider() == null) {
            return functionRequest.get(0);
        }
        return site.getErrorMessageProvider().getErrorMessage((String) functionRequest.get(0));
    }
}
