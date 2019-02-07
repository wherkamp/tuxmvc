package me.kingtux.tmvc.core;

/**
 * This is just the rules for the website
 */
public interface WebsiteRules {
    /**
     * The protocol either https or http
     *
     * @return the protocol
     */
    String protocol();

    /**
     * The host
     *
     * @return the host
     */
    String host();

    default String baseURL() {
        return protocol() + "://" + host();
    }

}
