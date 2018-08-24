package com.canyon.test;

import com.canyon.inject.Autowire;
import com.canyon.inject.Bean;

@Bean
public class B {

    @Autowire
    private A a;

    public void say() {
        System.out.println(a.name);
    }
}
