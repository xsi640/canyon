package com.canyon.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;

public class ConfigFactory {
    public static Config config = com.typesafe.config.ConfigFactory.parseResources("application.conf").resolve().getConfig("canyon");

    public static <T> T load(String path, Class<T> clazz) {
        return ConfigBeanFactory.create(config.getConfig(path), clazz);
    }

    public static String getString(String path) {
        return config.getString(path);
    }
}
