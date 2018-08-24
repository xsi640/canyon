package com.canyon.scan;

import com.canyon.inject.Bean;
import com.canyon.inject.Named;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述类
 */
public class ClassType<T> {
    private String name = "";
    private Class<T> clazz;
    private boolean single;
    private List<BaseDependentProperty> dependentPropertys;

    public ClassType() {
    }

    public ClassType(String name, Class<T> clazz, boolean single, List<BaseDependentProperty> dependentPropertys) {
        this.name = name;
        this.clazz = clazz;
        this.single = single;
        this.dependentPropertys = dependentPropertys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public List<BaseDependentProperty> getDependentPropertys() {
        return dependentPropertys;
    }

    public void setDependentPropertys(List<BaseDependentProperty> dependentPropertys) {
        this.dependentPropertys = dependentPropertys;
    }

    public static ClassType toClassType(Class<?> clazz) {
        Bean bean = clazz.getAnnotation(Bean.class);
        Named named = clazz.getAnnotation(Named.class);
        boolean singleton = false;
        if (bean != null)
            singleton = bean.singleton();
        return new ClassType(named == null ? "" : named.value(),
                clazz,
                singleton,
                new ArrayList());
    }
}
