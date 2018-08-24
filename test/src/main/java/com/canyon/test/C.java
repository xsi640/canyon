package com.canyon.test;

import com.canyon.boot.Boot;
import com.canyon.inject.Autowire;
import org.slf4j.Logger;

import java.util.List;

public class C extends Boot {

    @Autowire
    private Logger logger;

    @Autowire
    private B b;

    @Autowire
    private List<B> bl;


    @Override
    public void run() {
        b.say();

        for (B b1 : bl) {
            b1.say();
        }

        logger.info("aaa");
    }

    @Override
    public void destory() {

    }
}
