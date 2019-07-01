package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxjsitemap.SiteMap;
import me.kingtux.tuxjsitemap.SiteMapGenerator;
import me.kingtux.tuxjsitemap.SiteURL;
import me.kingtux.tuxmvc.TuxMVC;
import me.kingtux.tuxmvc.core.controller.SingleController;
import me.kingtux.tuxmvc.core.controller.SingleSitemapHandler;
import me.kingtux.tuxmvc.core.request.RequestType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SiteMapAuto extends Thread {
    private File folder = new File("sitemap");
    private SimpleWebsite website;

    SiteMapAuto(SimpleWebsite simpleWebsite) {
        this.website = simpleWebsite;
    }

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            TuxMVC.TUXMVC_LOGGER.debug("Creating SiteMap!");
            SiteMap map;
            SiteMapGenerator generator = new SiteMapGenerator(website.getSiteRules().baseURL());
            List<SingleController> controllers = new ArrayList<>(website.getControllerList());
            for (SingleController sc : controllers) {
                if (sc.getRequestType() == RequestType.GET && sc.sitemap()) generator.addURL(sc.getPath().substring(1));
            }
            List<SingleSitemapHandler> singleSitemapHandlers = new ArrayList<>(website.getSSHs());

            for (SingleSitemapHandler singleSitemapHandler : singleSitemapHandlers) {

                List<SiteURL> urls = singleSitemapHandler.execute(website);
                urls.forEach(generator::addURL);
            }

            map = generator.build();

            try {
                map.writeToFolder(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sleep(900000);
            } catch (InterruptedException e) {
                TuxMVC.TUXMVC_LOGGER.error("Unable to sleep thread", e);
            }
        }
    }
}
