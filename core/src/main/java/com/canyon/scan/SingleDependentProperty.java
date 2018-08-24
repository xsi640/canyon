package com.canyon.scan;

import com.canyon.inject.InjectProvider;

import java.lang.reflect.Field;

public class SingleDependentProperty extends BaseDependentProperty {
    private ClassType<?> classType = null;

    public SingleDependentProperty() {
    }

    public SingleDependentProperty(String name, Field field, InjectProvider provider, ClassType<?> classType) {
        super(name, field, provider);
        this.classType = classType;
    }

    public ClassType<?> getClassType() {
        return classType;
    }

    public void setClassType(ClassType<?> classType) {
        this.classType = classType;
    }
}
