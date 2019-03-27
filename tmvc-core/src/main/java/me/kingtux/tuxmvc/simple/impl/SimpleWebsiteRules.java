package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxmvc.core.WebsiteRules;

public class SimpleWebsiteRules implements WebsiteRules {
    private String protocol;
    private String host;

    public SimpleWebsiteRules(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
    }

    @Override
    public String protocol() {
        return protocol;
    }

    @Override
    public String host() {
        return host;
    }
}
