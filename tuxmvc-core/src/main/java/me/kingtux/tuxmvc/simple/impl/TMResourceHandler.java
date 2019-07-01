package me.kingtux.tuxmvc.simple.impl;


import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.http.staticfiles.StaticFileConfig;
import me.kingtux.tuxjsql.core.TuxJSQL;
import me.kingtux.tuxmvc.TuxMVC;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.request.MimeType;
import me.kingtux.tuxmvc.core.rg.Resource;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxmvc.core.rg.templategrabbers.ExternalResourceGrabber;
import me.kingtux.tuxmvc.core.rg.templategrabbers.IEResourceGrabber;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TMResourceHandler implements ResourceHandler {
    private ResourceGrabber grabber = new IEResourceGrabber("public");
private Website website;
    private ResourceGrabber siteMapGrabber;
    private SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    public TMResourceHandler(Website website) {
        this.website = website;
        if (Boolean.parseBoolean(website.getInternalProperties().getProperty("sitemap.auto", "true"))) {

            siteMapGrabber = new ExternalResourceGrabber("sitemap");
        }
    }

    @Override
    public void addStaticFileConfig(@NotNull StaticFileConfig staticFileConfig) {
        TuxMVC.TUXMVC_LOGGER.warn("Adding static files configs are not enabled!");
    }

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        String result = url.substring(url.lastIndexOf('.') + 1).trim();
        response.setContentType(MimeType.getMimeTypeFromExtension(result).getMimeType());
        request.setAttribute("handled-as-static-file", true);
        TuxMVC.TUXMVC_LOGGER.debug(response.getContentType()+ " Was found for "+ result);
        URL urlForFile = null;
        if (url.toLowerCase().contains("sitemap") && Boolean.parseBoolean(website.getInternalProperties().getProperty("sitemap.directory", "true"))) {
            TuxJSQL.logger.debug(url);
            Resource resource = siteMapGrabber.getResource(url.substring(1));
            if (resource != null) urlForFile = resource.getUrl();
        }
        if (url.toLowerCase().startsWith("/assets/libs/") && urlForFile == null) {

            String libPath = url.replace("/assets/libs/", "");

            StringBuilder path = new StringBuilder();
            if (TuxMVC.class.getResource("/META-INF/resources/webjars/" + libPath) != null) {
                path = new StringBuilder("/META-INF/resources/webjars/" + libPath);
            } else {
                //Get the Version using random apis!
                String[] split = libPath.split("/");
                String localPath;
                WebJarAssetLocator locator = new WebJarAssetLocator();
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < split.length; i++) {
                    if (i != 1) builder.append("/");
                    builder.append(split[i]);
                }
                try {
                    localPath = locator.getFullPath(split[0], builder.toString());
                    path = new StringBuilder("/" + localPath);
                } catch (IllegalArgumentException ignored) {

                }
            }

            if ((!path.toString().isEmpty()) && TuxMVC.class.getResource(path.toString()) != null) {
                TuxMVC.TUXMVC_LOGGER.debug("Locating Internal Library File " + path.toString());
                response.setHeader("Cache-Control", "public, max-age=31536000");
                urlForFile = TuxMVC.class.getResource(path.toString());
            }

        }
        if (urlForFile == null) {
            urlForFile = grabber.getResource(url.substring(1)).getUrl();
            //.getFile(url.substring(1));
        }
        if (urlForFile == null) return false;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        response.setHeader("Expires", format.format(cal.getTime()));
        try {

            IOUtils.copy(urlForFile.openStream(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
