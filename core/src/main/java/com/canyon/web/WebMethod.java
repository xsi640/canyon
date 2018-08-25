package com.canyon.web;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebMethod {
    HttpMethod[] method();
    String request() default MediaType.ALL;
    String response() default MediaType.ALL;
}
