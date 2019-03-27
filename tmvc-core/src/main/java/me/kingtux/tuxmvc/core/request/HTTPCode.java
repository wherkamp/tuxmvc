package me.kingtux.tuxmvc.core.request;

/**
 * @author Mobmaker55
 */
public enum HTTPCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    PARTIAL_CONTENT(206),
    MOVED(301),
    USE_PROXY(305),
    TEMP_REDIRECT(307),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    REQUEST_TIMEOUT(408),
    UNAVAILABLE_LEGAL(451),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504);

    private int code;

    HTTPCode(int code) {
        this.code = code;
    }

    public int getCode() {

        return code;
    }
}