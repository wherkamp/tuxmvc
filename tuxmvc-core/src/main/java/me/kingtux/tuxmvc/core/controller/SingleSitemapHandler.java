package me.kingtux.tuxmvc.core.controller;

import me.kingtux.tuxjsitemap.SiteURL;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.annotations.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SingleSitemapHandler {
    private Method method;
    private Object cl;

    public SingleSitemapHandler(Object c, Method method) {
        this.method = method;
        cl = c;
    }


    @SuppressWarnings("All")
    public List<SiteURL> execute(Website website) {
        List<SiteURL> list = new ArrayList<>();
        try {
            list = (List<SiteURL>) method.invoke(cl);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return list;
    }




}
