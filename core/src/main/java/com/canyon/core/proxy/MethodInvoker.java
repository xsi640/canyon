package com.canyon.core.proxy;

import java.lang.reflect.Method;

public interface MethodInvoker {
    <T> Object invoke(Class<T> clazz, Method method, Object[] args);
}
