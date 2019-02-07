package me.kingtux.tmvc.core.annotations;

import me.kingtux.tmvc.core.request.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Path {
    /**
     * The path
     * Examples:
     *  /
     *  /hey/:who
     *  /hey/*
     * @return the path
     */
    String path();

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
