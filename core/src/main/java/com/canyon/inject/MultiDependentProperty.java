package com.canyon.inject;

import com.canyon.scan.BaseDependentProperty;
import com.canyon.scan.ClassType;

import java.lang.reflect.Field;
import java.util.List;

public class MultiDependentProperty extends BaseDependentProperty {
    private List<ClassType> classTypes;

    public MultiDependentProperty(String name, Field field, InjectProvider injectProvider, List<ClassType> classTypes) {
        super(name, field, injectProvider);
        this.classTypes = classTypes;
    }

    public List<ClassType> getClassTypes() {
        return classTypes;
    }

    public void setClassTypes(List<ClassType> classTypes) {
        this.classTypes = classTypes;
    }
}
