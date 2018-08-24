package com.canyon.inject;

/**
 * 对于没有实例化的Bean，需要注入，需要实现此接口
 * 如：Config配置
 */
public interface InjectProvider {
    boolean isMatch(Class<?> clazz);

    <T> T create(Class<T> objClazz, Class<T> fieldClazz);
}
