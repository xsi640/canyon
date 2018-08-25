package com.canyon.web.parser;

import com.canyon.inject.Bean;

import java.util.Date;

@Bean
public class WebParamConverterDate implements WebParamConverter<Date> {
    @Override
    public Date convert(String value) {
        return new Date(Long.parseLong(value));
    }
}
