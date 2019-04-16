package me.kingtux.tuxmvc.simple.impl;

import io.javalin.staticfiles.ResourceHandler;
import io.javalin.staticfiles.StaticFileConfig;
import me.kingtux.tuxmvc.TuxMVC;
import me.kingtux.tuxmvc.core.request.MimeType;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxmvc.core.rg.templategrabbers.IEResourceGrabber;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class TMResourceHandler implements ResourceHandler {
    private ResourceGrabber grabber = new IEResourceGrabber("public");


    @Override
    public void addStaticFileConfig(@NotNull StaticFileConfig staticFileConfig) {
        TuxMVC.TUXMVC_LOGGER.warn("Adding static files configs are not enabled!");
    }

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        String result = url.substring(url.lastIndexOf('.') + 1).trim();
        response.setContentType(MimeType.getMimeTypeFromExtension(result).getMimeType());
        TuxMVC.TUXMVC_LOGGER.debug(response.getContentType()+ " Was found for "+ result);
        URL urlForFile = null;
        request.setAttribute("handled-as-static-file", true);
        if (url.toLowerCase().startsWith("/assets/libs/")) {
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
                urlForFile = TuxMVC.class.getResource(path.toString());
            }

        }
        if (urlForFile == null) {
            urlForFile = grabber.getFile(url);
        }
        if (urlForFile == null) return false;
        try {
            IOUtils.copy(urlForFile.openStream(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
