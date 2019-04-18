package me.kingtux.tuxmvc.core;

/**
 * This is just the rules for the website
 */
public class WebsiteRules {
    private String url, name;

    public WebsiteRules(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public String toString() {
        return "WebsiteRules{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getHost() {
        return url.replace("https://", "").replace("http://", "");
    }

    public String baseURL() {
        return url;
    }

    public String baseWSURL() {
        return url.toLowerCase().startsWith("https://") ? "wss" : "ws" + "://" + getHost();
    }

    public String name() {
        return name;
    }
}
