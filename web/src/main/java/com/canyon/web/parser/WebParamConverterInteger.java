package com.canyon.web.parser;

import com.canyon.commons.TypeParse;
import com.canyon.inject.Bean;

@Bean
public class WebParamConverterInteger implements WebParamConverter<Integer> {
    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
