package me.kingtux.tuxmvc.simple.impl;

import io.javalin.websocket.ConnectHandler;
import io.javalin.websocket.WsSession;
import me.kingtux.tuxmvc.core.ws.WSSession;

public class SimpleWSSession implements WSSession {
    public SimpleWSSession(WsSession session) {
    }
}
