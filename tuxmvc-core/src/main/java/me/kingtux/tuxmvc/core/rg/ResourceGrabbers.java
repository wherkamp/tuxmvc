package me.kingtux.tuxmvc.core.rg;


import me.kingtux.tuxmvc.core.rg.templategrabbers.ExternalResourceGrabber;
import me.kingtux.tuxmvc.core.rg.templategrabbers.IEResourceGrabber;
import me.kingtux.tuxmvc.core.rg.templategrabbers.InternalResourceGrabber;

import java.lang.reflect.InvocationTargetException;

public enum ResourceGrabbers {
    INTERNAL_EXTERNAL_GRABBER(IEResourceGrabber.class),
    INTERNAL_GRABBER(InternalResourceGrabber.class),
    EXTERNAL_GRABBER(ExternalResourceGrabber.class);


    private Class<?> clazz;

    ResourceGrabbers(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ResourceGrabber build(String path) {
        try {
            return (ResourceGrabber) clazz.getConstructor(String.class).newInstance(path);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
