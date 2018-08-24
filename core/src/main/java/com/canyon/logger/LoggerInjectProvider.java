package com.canyon.logger;

import com.canyon.inject.InjectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerInjectProvider implements InjectProvider {
    @Override
    public boolean isMatch(Class<?> clazz) {
        return clazz.equals(Logger.class);
    }

    @Override
    public <T> T create(Class<T> objClazz, Class<T> fieldClazz) {
        return (T) LoggerFactory.getLogger(objClazz);
    }
}
