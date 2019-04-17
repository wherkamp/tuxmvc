package me.kingtux.tuxmvc;

import me.kingtux.tuxjsql.core.TuxJSQL;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.core.rg.ResourceGrabber;
import me.kingtux.tuxmvc.core.rg.ResourceGrabbers;
import me.kingtux.tuxmvc.simple.db.SimpleDatabaseManager;
import me.kingtux.tuxmvc.simple.impl.SimpleWebsite;
import me.kingtux.tuxmvc.simple.impl.email.SimpleEmailManager;
import me.kingtux.tuxorm.TOConnection;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WebsiteBuilder {
    private Properties siteProperties = new Properties();

    private WebsiteBuilder() {
    }

    public static Website buildFromProperties(Properties p) {
        String host = p.getProperty("site.url");
        File file = new File(p.getProperty("site.static", "public"));
        int port = Integer.parseInt(p.getProperty("site.port"));

        ResourceGrabber tg = ResourceGrabbers.valueOf(p.getProperty("template.grabber", "INTERNAL_EXTERNAL_GRABBER")).build(p.getProperty("template.path"));
        SimpleEmailManager.SEmailBuilder em = SimpleEmailManager.buildEmailManager(p.getProperty("email.host", ""), p.getProperty("email.port", ""),
                p.getProperty("email.ts", "SMTP"), p.getProperty("email.from",""), p.getProperty("email.password", ""), p.getProperty("email.from.name", ""));
        TuxJSQL.setup(p);
        TOConnection connection = new TOConnection();
        DatabaseManager dbManager = new SimpleDatabaseManager(connection);
        SslContextFactory factory = null;
        int sslPort = 0;
        if (Boolean.parseBoolean(p.getProperty("site.ssl"))) {
            factory = getSslContextFactory(p.getProperty("site.ssl.file"), p.getProperty("site.ssl.password"));
            sslPort = Integer.parseInt(p.getProperty("site.ssl.port"));
        }
        return new SimpleWebsite(new WebsiteRules(host, p.getProperty("site.name")), port, file, tg, em, dbManager, factory, sslPort, p.getProperty("tempalte.extension"));
    }

    public static WebsiteBuilder buildFromScratch() {
        return new WebsiteBuilder();
    }

    public static WebsiteBuilder buildFromAlreadyExistingProperties(Properties properties) {
        WebsiteBuilder builder = new WebsiteBuilder();
        builder.siteProperties = new Properties();
        return builder;
    }

    public WebsiteBuilder baseURL(String url) {
        siteProperties.setProperty("site.url", url);
        return this;
    }

    public Website build() {
        return buildFromProperties(siteProperties);
    }

    public static SslContextFactory getSslContextFactory(String file, String password) {
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(file);
        sslContextFactory.setKeyStorePassword(password);
        return sslContextFactory;
    }


    public static Website createSiteFromLocalProperties() {
        File file = new File("tmsite.properties");
        if(!file.exists()){
            //TODO saveFile
        }
        return createSiteFromFile(file);
    }

    public static Website createSiteFromFile(File file) {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buildFromProperties(properties);
    }
}
