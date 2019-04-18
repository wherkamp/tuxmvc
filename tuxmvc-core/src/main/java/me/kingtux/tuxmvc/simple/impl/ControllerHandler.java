package me.kingtux.tuxmvc.simple.impl;

import io.javalin.Context;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.controller.ControllerExeception;
import me.kingtux.tuxmvc.core.controller.ControllerExecutor;
import me.kingtux.tuxmvc.core.controller.SingleController;

public class ControllerHandler {
    private SingleController sc;
    private Website website;

    public ControllerHandler(SingleController sc) {
        this.sc = sc;
    }

    public ControllerHandler(SingleController sc, Website website) {
        this.sc = sc;
        this.website = website;
    }

    public void execute(Context ctx) {
        if(website.getSiteRules().baseURL().equals("{PFFC}")){
            ((SimpleWebsite)website).setWebsiteRules(new WebsiteRules(ctx.host(), website.getSiteRules().name()));
        }
        try {
            ControllerExecutor se = sc.buildExecutor(new SimpleRequest(ctx, sc.getRequestType(), website), website.getViewManager(), website);
            se.execute();
        } catch (ControllerExeception controllerExeception) {
            controllerExeception.printStackTrace();
        }
    }
}
