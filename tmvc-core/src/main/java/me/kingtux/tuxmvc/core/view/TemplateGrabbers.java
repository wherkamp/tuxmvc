package me.kingtux.tuxmvc.core.view;

import me.kingtux.tuxmvc.core.view.templategrabbers.ExternalTemplateGrabber;
import me.kingtux.tuxmvc.core.view.templategrabbers.IETemplateGrabber;
import me.kingtux.tuxmvc.core.view.templategrabbers.InternalTemplateGrabber;

import java.lang.reflect.InvocationTargetException;

public enum TemplateGrabbers {
    IE_TEMPLATE_GRABBER(IETemplateGrabber.class),
    INTERNAL_TEMPLATE_GRABBER(InternalTemplateGrabber.class),
    EXTERNAL_TEMPLATE_GRABBER(ExternalTemplateGrabber.class);


    private Class<?> clazz;

    TemplateGrabbers(Class<?> clazz) {
        this.clazz = clazz;
    }
    public TemplateGrabber build(String extension, String path){
        try {
            return (TemplateGrabber) clazz.getConstructor(String.class, String.class).newInstance(path, extension);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
