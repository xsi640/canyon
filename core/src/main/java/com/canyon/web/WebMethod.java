package com.canyon.web;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebMethod {
    HttpMethod[] method();
    MediaType request() default MediaType.ALL;
    MediaType response() default MediaType.ALL;
}
