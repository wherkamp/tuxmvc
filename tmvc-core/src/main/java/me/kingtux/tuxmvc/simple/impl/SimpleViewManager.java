package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.request.Request;
import me.kingtux.tuxmvc.core.view.TemplateGrabber;
import me.kingtux.tuxmvc.core.view.View;
import me.kingtux.tuxmvc.core.view.ViewManager;
import me.kingtux.tuxmvc.core.view.ViewVariableGrabber;
import me.kingtux.tuxmvc.core.view.templategrabbers.ExternalTemplateGrabber;
import me.kingtux.tuxmvc.simple.TMSUtils;
import me.kingtux.tuxmvc.simple.jtwig.RouteFunction;
import me.kingtux.tuxmvc.simple.jtwig.TuxResourceService;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.reference.ResourceReference;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SimpleViewManager implements ViewManager {
    private TemplateGrabber templateGrabber;
    private Map<String, Object> defaultVariables = new HashMap<>();
    private Map<String, ViewVariableGrabber> viewVariableGrabbers = new HashMap<>();
    private EnvironmentConfiguration configurtation;

    public SimpleViewManager(TemplateGrabber templateGrabber, Website website) {
        this.templateGrabber = templateGrabber;
        if(this.templateGrabber ==null){
            this.templateGrabber = new ExternalTemplateGrabber(new File("templates"));
        }
        // sr.host sr.protocol and sr.baseURL
        registerDefaultViewVariable("sr", website.getSiteRules());

        configurtation = EnvironmentConfigurationBuilder.configuration().functions().add(new RouteFunction(website)).and().build();
    }

    @Override
    public void setTemplateGrabber(TemplateGrabber templateGrabber) {
        this.templateGrabber = templateGrabber;
    }

    @Override
    public TemplateGrabber getTemplateGrabber() {
        return templateGrabber;
    }

    @Override
    public String parseView(View view) {
        // Just understand if this breaks I will be on the floor crying
        Environment environment = new EnvironmentFactory().create(configurtation);
        environment.getResourceEnvironment();
        TMSUtils.setFieldValue(environment.getResourceEnvironment(), new TuxResourceService(this), "resourceService");
        String s = getTemplateGrabber().getFile(view.getTemplate());
        if (s == null) return null;
        JtwigTemplate jtwigTemplate = new JtwigTemplate(environment, ResourceReference.inline(s));
        return jtwigTemplate.render(((SimpleView) view).getjTwigModel());
    }

    @Override
    public View buildView(Website website, Request request) {
        View view = new SimpleView();
        defaultVariables.forEach(view::set);
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


}
