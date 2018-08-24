package com.canyon.commons;

public class Ref<T> {
    private T value;

    public Ref() {
    }

    public Ref(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value == null ? null : value.toString();
    }
}
