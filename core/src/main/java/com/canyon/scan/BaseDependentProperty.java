package com.canyon.scan;

import com.canyon.inject.InjectProvider;

import java.lang.reflect.Field;

public abstract class BaseDependentProperty {
    private String name = "";
    private Field field = null;
    private InjectProvider injectProvider = null;

    public BaseDependentProperty() {
    }

    public BaseDependentProperty(String name, Field field, InjectProvider injectProvider) {
        this.name = name;
        this.field = field;
        this.injectProvider = injectProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public InjectProvider getInjectProvider() {
        return injectProvider;
    }

    public void setInjectProvider(InjectProvider injectProvider) {
        this.injectProvider = injectProvider;
    }
}
