package me.kingtux.tmvc.core.request;

public enum HTTPCode {
    OK(200),
    TEMP_REDIRECT(302),
    REDIRECT(301);
    private int code;

    HTTPCode(int code) {
        this.code = code;
    }

    public int getCode() {

        return code;
    }
}
