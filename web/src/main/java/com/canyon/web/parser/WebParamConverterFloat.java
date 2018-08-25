package com.canyon.web.parser;

import com.canyon.commons.TypeParse;
import com.canyon.inject.Bean;

@Bean
public class WebParamConverterFloat implements WebParamConverter<Float> {
    @Override
    public Float convert(String value) {
        return Float.parseFloat(value);
    }
}
