package com.canyon.web;

import java.lang.annotation.*;

@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebParam {
    String name() default "";

    From from() default From.ANY;

    String defaultValue() default "";
}
