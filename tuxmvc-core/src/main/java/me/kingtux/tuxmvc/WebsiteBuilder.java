package me.kingtux.tuxmvc;

import dev.tuxjsql.core.TuxJSQLBuilder;
import me.kingtux.tuxmvc.core.Environment;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.WebsiteRules;
import me.kingtux.tuxmvc.core.model.DatabaseManager;
import me.kingtux.tuxmvc.simple.db.SimpleDatabaseManager;
import me.kingtux.tuxmvc.simple.impl.SimpleWebsite;
import me.kingtux.tuxmvc.simple.impl.email.SimpleEmailManager;
import me.kingtux.tuxorm.TOConnection;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * To build a website use this class
 *
 * @author KingTux
 */
public class WebsiteBuilder {
    private Properties siteProperties = new Properties();

    private WebsiteBuilder() {
    }

    /**
     * This will create a website via a Properties Object
     *
     * @param p properties with all the rules
     * @return the configured and built website!
     */
    public static Website buildFromProperties(Properties p) {
        p = BetterProperties.ptobp(p);

        //Bare Mininmum work
        String host = p.getProperty("site.url", "{PFFC}");
        WebsiteRules rules = new WebsiteRules(host, p.getProperty("site.name", ""));
        int port = Integer.parseInt(p.getProperty("site.port", "2345"));
        //Email Work
        SimpleEmailManager.SEmailBuilder em = SimpleEmailManager.buildEmailManager(p.getProperty("email.host", ""), p.getProperty("email.port", ""),
                p.getProperty("email.ts", "SMTP"), p.getProperty("email.from",""), p.getProperty("email.password", ""), p.getProperty("email.from.name", ""));

        //Database Work
        TOConnection connection;
        if(p.getProperty("db.type", null)==null){
            Properties tempDB = new Properties();
            tempDB.setProperty("db.type", "dev.tuxjsq.sqlite.SQLiteBuilder");
            tempDB.setProperty("db.file", "db.db");
            connection = new TOConnection(TuxJSQLBuilder.create(tempDB));
        }else
            connection = new TOConnection(TuxJSQLBuilder.create(p));
        DatabaseManager dbManager = new SimpleDatabaseManager(connection);
        //SSL Work
        SslContextFactory factory = null;
        int sslPort = 0;
        if (Boolean.parseBoolean(p.getProperty("site.ssl","false"))) {
            factory = getSslContextFactory(p.getProperty("site.ssl.file"), p.getProperty("site.ssl.password"));
            sslPort = Integer.parseInt(p.getProperty("site.ssl.port"));
        }
        //Create Website
        return new SimpleWebsite(rules, port, em, dbManager, factory, sslPort, Environment.valueOf(p.getProperty("site.env", "DEBUG")));
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

    public WebsiteBuilder name(String name) {
        siteProperties.setProperty("site.name",name);
        return this;
    }
    public WebsiteBuilder port(int port){
        siteProperties.setProperty("site.port", String.valueOf(port));
        return this;
    }
    public WebsiteBuilder ssl(String file, String password, int port){
        siteProperties.setProperty("site.ssl", "true");
        siteProperties.setProperty("site.ssl.port", String.valueOf(port));
        siteProperties.setProperty("site.ssl.file", file);
        siteProperties.setProperty("site.ssl.password", password);

        return this;
    }

    public Website build() {
        return buildFromProperties(siteProperties);
    }

    public static SslContextFactory getSslContextFactory(String file, String password) {
        SslContextFactory sslContextFactory = new SslContextFactory.Server();
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
