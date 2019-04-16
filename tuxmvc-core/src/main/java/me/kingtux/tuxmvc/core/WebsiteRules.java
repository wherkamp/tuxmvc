package me.kingtux.tuxmvc.core;

/**
 * This is just the rules for the website
 */
public class WebsiteRules {
    private String protocol, host, name;

    public WebsiteRules(String protocol, String host, String name) {
        this.protocol = protocol;
        this.host = host;
        this.name = name;
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

    public String baseWSURL() {
        return protocol.equals("https") ? "wss" : "ws" + "://" + host;
    }

    public String name() {
        return name;
    }
}
