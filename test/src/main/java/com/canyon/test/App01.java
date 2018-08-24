package com.canyon.test;

import com.canyon.boot.ApplicationContext;
import com.canyon.boot.Boot;
import com.canyon.inject.Autowire;
import com.canyon.inject.Bean;

import java.util.List;

public class App01 {
    public static void main(String[] args) {
        new ApplicationContext("com.canyon").run();
    }
}


