package me.kingtux.tuxmvc.core.request;

/**
 * The Request Type
 */
public enum RequestType {
    /**
     * The most basic type of request. This is what usually happens when a browser goes to WEBSITE/path
     *
     */
    GET,
    /**
     * Post usually used when inserting or signing up or loging in
     */
    POST,
    PUT,
    DELETE;
}
