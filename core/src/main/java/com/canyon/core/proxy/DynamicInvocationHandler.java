package com.canyon.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler<T> implements InvocationHandler {
    private Class<T> clazz;
    private MethodInvoker methodInvoker;

    public DynamicInvocationHandler(Class<T> clazz, MethodInvoker methodInvoker) {
        this.clazz = clazz;
        this.methodInvoker = methodInvoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return methodInvoker.invoke(this.clazz, method, args);
    }
}
