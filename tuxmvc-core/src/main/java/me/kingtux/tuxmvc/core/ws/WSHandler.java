package me.kingtux.tuxmvc.core.ws;
public interface WSHandler {
    void onConnect(WSSession session);

    void onClose(WSSession session, int statusCode, String reason);

    void onError(WSSession session, Throwable throwable);

    void onMessage(WSSession session, String message);

}
