package com.canyon.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeRef<T> {

    private static ConcurrentMap<Type, Type> classTypeCache
            = new ConcurrentHashMap<>(16, 0.75f, 1);

    private Type type;

    public TypeRef() {
        Type superClass = getClass().getGenericSuperclass();

        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        Type cachedType = classTypeCache.get(type);
        if (cachedType == null) {
            classTypeCache.putIfAbsent(type, type);
            cachedType = classTypeCache.get(type);
        }
        this.type = cachedType;
    }

    public Type getType() {
        return this.type;
    }
}
