package com.canyon.inject;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    boolean singleton() default false;
}
