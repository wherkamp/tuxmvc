package me.kingtux.tuxmvc.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {

    String key();

    /**
     * Cookies, FILE, Session does not support this
     * @return the default value
     */
    String defaultValue() default "";

    Type type() default Type.REQUEST;



    //TODO add support for required
    // boolean required() default true;

    /**
     * Where the value would be
     */
    enum Type {
        /**
         * This means it was a query param. If the request type was get it will do a get if it was POST it will be post
         */
        REQUEST,
        /**
         * If you have get varaibles even if the request type isnt get
         */
        GET,
        /**
         * A request param
         */
        URL,
        /**
         * This is a file idiot
         */
        FILE
    }
}
