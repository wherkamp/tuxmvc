package me.kingtux.tuxmvc.core.ws;

public interface WSSession {
    void send(String s);

    String getSessionID();

    String queryParam(String s);
     String queryParam(String key, String def);

     void close();

     void close(int i, String message);
}
