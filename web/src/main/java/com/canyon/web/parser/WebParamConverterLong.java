package com.canyon.web.parser;

import com.canyon.commons.TypeParse;
import com.canyon.inject.Bean;

@Bean
public class WebParamConverterLong implements WebParamConverter<Long> {
    @Override
    public Long convert(String value) {
        return Long.parseLong(value);
    }
}
