package me.kingtux.tuxmvc.core.emails;

public class EmailSettings {
    private String host, username, password;
    private int port;
    private TransportStrategy transportStrategy;

    public EmailSettings(String host, String username, String password, int port, TransportStrategy transportStrategy) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.transportStrategy = transportStrategy;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public TransportStrategy getTransportStrategy() {
        return transportStrategy;
    }
}
