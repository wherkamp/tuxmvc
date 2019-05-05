package me.kingtux.tuxmvc.core.annotations;

import me.kingtux.tuxmvc.core.request.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Controller {
    /**
     * The path
     * Examples:
     *  /
     *  /hey/:who
     *  /hey/*
     * @return the path
     */
    String path();

    boolean sitemap() default true;
    /**
     * The request type
     * @return type
     */
    RequestType requestType() default RequestType.GET;

    /**
     * If unset it will need to be set while the method is called
     * @return the template path
     */
    String template() default "";
}
