package me.kingtux.tuxmvc.core;

/**
 * This is just the rules for the website
 */
public class WebsiteRules {
    private String protocol, host;

    public WebsiteRules(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String baseURL() {
        return protocol + "://" + host;
    }

}
