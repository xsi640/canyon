package com.canyon.web;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebParam {
    String name() default "";

    From from() default From.ANY;

    String defaultValue() default "";
}
