package com.canyon.web.parser;

import com.canyon.commons.TypeParse;
import com.canyon.inject.Bean;

@Bean
public class WebParamConverterDouble implements WebParamConverter<Double> {
    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }
}
