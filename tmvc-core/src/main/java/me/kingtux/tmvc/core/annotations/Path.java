package me.kingtux.tmvc.core.annotations;

import me.kingtux.tmvc.core.request.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Path {
    String path();

    RequestType requestType() default RequestType.GET;
}
