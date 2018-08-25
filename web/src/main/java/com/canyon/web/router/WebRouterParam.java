package com.canyon.web.router;

import com.canyon.web.From;

public class WebRouterParam {
    private String name;
    private From from;
    private String defaultValue;
    private Class<?> clazz;

    public WebRouterParam(String name, From from, String defaultValue, Class<?> clazz) {
        this.name = name;
        this.from = from;
        this.defaultValue = defaultValue;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
