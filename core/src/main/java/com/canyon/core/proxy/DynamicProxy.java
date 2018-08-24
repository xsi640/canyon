package com.canyon.core.proxy;

import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static <T> Object newInstance(Class<T> clazz, MethodInvoker invoker) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new DynamicInvocationHandler(clazz, invoker));
    }
}
