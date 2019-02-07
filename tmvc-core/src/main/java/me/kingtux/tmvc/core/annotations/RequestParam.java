package me.kingtux.tmvc.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {

    String key();

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
        FILE,
        /**
         * Gets the value from the session
         */
        SESSION,
        /**
         * gets the value from the cookies!
         */
        COOKIE;
    }
}
