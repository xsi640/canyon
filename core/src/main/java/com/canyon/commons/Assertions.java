package com.canyon.commons;

import java.util.Collection;

public class Assertions {
    public <T> T notNull(String name, T value) {
        if (value == null)
            throw new IllegalArgumentException(StringUtils.format("{} can not be null", name));
        return value;
    }

    public <T> T notNullOrEmpty(String name, T value) {
        if (value == null)
            throw new IllegalArgumentException(StringUtils.format("{} can not be empty", name));
        if (value instanceof Collection && ((Collection) value).isEmpty())
            throw new IllegalArgumentException(StringUtils.format("{} can not be empty", name));
        if (value.getClass().isArray() && ((Object[]) value).length == 0)
            throw new IllegalArgumentException(StringUtils.format("{} can not be empty", name));
        if (value.toString().isEmpty())
            throw new IllegalArgumentException(StringUtils.format("{} can not be empty", name));
        return value;
    }

    public void isTrue(String name, Boolean value) {
        if (!value) {
            throw new IllegalArgumentException(StringUtils.format("{} must be true", name));
        }
    }

    public void isFalse(String name, Boolean value) {
        if (value) {
            throw new IllegalArgumentException(StringUtils.format("{} must be true", name));
        }
    }
}
