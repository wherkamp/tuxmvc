package me.kingtux.tuxmvc.simple.impl;

import io.javalin.websocket.WsContext;
import me.kingtux.tuxmvc.core.ws.WSSession;

import java.util.Objects;

public class SimpleWSSession implements WSSession {
    private WsContext session;
    private String sessionID;

    public SimpleWSSession(WsContext session) {
        this.session = session;

    }

    public String queryParam(String key) {
        return session.queryParam(key,null);
    }

    public void close() {
        session.session.close();
    }

    public void close(int i, String message) {
        session.session.close(i, message);
    }

    public String queryParam(String key, String def) {
        return session.queryParam(key, def);
    }

    public void send(String s) {
        session.send(s);
    }

    public String getSessionID() {
        return sessionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleWSSession that = (SimpleWSSession) o;
        return Objects.equals(sessionID, that.sessionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionID);
    }
}
