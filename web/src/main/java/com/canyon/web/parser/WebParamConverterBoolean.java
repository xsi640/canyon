package com.canyon.web.parser;

import com.canyon.inject.Bean;

@Bean
public class WebParamConverterBoolean implements WebParamConverter<Boolean> {
    @Override
    public Boolean convert(String value) {
        return Boolean.parseBoolean(value);
    }
}
