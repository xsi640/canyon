package com.canyon.web.parser;

import com.canyon.commons.TypeParse;
import com.canyon.inject.Bean;

@Bean
public class WebParamConverterShort implements WebParamConverter<Short> {
    @Override
    public Short convert(String value) {
        return Short.parseShort(value);
    }
}
