package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.request.Request;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxmvc.core.view.View;
import me.kingtux.tuxmvc.core.view.ViewManager;
import me.kingtux.tuxmvc.core.view.ViewVariableGrabber;
import me.kingtux.tuxmvc.simple.TMSUtils;
import me.kingtux.tuxmvc.simple.jtwig.RouteFunction;
import me.kingtux.tuxmvc.simple.jtwig.TuxResourceService;
import org.apache.commons.io.IOUtils;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.reference.ResourceReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleViewManager implements ViewManager {
    private ResourceGrabber resourceGrabber;
    private Map<String, Object> defaultVariables = new HashMap<>();
    private Map<String, ViewVariableGrabber> viewVariableGrabbers = new HashMap<>();
    private EnvironmentConfiguration configurtation;
    private String extension;

    public SimpleViewManager(ResourceGrabber resourceGrabber, Website website, String property) {
        this.resourceGrabber = resourceGrabber;
        // sr.host sr.protocol and sr.baseURL
        if (website != null) {
            registerDefaultViewVariable("sr", website.getSiteRules());
            configurtation = EnvironmentConfigurationBuilder.configuration().functions().add(new RouteFunction(website)).and().build();
        }
        extension = property == null || property.isEmpty() ? ".html" : property;
    }

    @Override
    public ResourceGrabber getResourceGrabber() {
        return resourceGrabber;
    }

    @Override
    public void setResourceGrabber(ResourceGrabber resourceGrabber) {
        this.resourceGrabber = resourceGrabber;
    }

    @Override
    public String parseView(ResourceGrabber grabber, View view) {
        if (!view.getTemplate().toLowerCase().endsWith(extension)) {
            view.setTemplate(view.getTemplate() + extension);
        }
        // Just understand if this breaks I will be on the floor crying
        Environment environment = new EnvironmentFactory().create(configurtation);
        environment.getResourceEnvironment();
        TMSUtils.setFieldValue(environment.getResourceEnvironment(), new TuxResourceService(this), "resourceService");
        String s = null;
        try {
            s = new String(IOUtils.toByteArray(grabber.getFile(view.getTemplate())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (s == null) return null;
        JtwigTemplate jtwigTemplate = new JtwigTemplate(environment, ResourceReference.inline(s));
        return jtwigTemplate.render(((SimpleView) view).getjTwigModel());
    }

    @Override
    public View buildView(Website website, Request request) {
        View view = new SimpleView();
        defaultVariables.forEach(view::set);
        if (request != null)
            viewVariableGrabbers.forEach((s, viewVariableGrabber) -> view.set(s, viewVariableGrabber.get(request)));
        return view;
    }


    @Override
    public void registerDefaultViewVariable(String s, Object o) {
        if (o instanceof ViewVariableGrabber) {
            registerViewVariableGrabber(s, (ViewVariableGrabber) o);
            return;
        }
        defaultVariables.put(s, o);
    }

    @Override
    public void registerViewVariableGrabber(String s, ViewVariableGrabber viewVariableGrabber) {
        viewVariableGrabbers.put(s, viewVariableGrabber);
    }

    @Override
    public String getExtension() {
        return extension;
    }


}
