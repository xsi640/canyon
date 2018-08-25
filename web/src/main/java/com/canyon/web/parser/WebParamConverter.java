package com.canyon.web.parser;

public interface WebParamConverter<T> {
    T convert(String value);
}
