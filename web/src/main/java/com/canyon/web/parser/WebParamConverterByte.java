package com.canyon.web.parser;

import com.canyon.commons.TypeParse;
import com.canyon.inject.Bean;

@Bean
public class WebParamConverterByte implements WebParamConverter<Byte> {
    @Override
    public Byte convert(String value) {
        return Byte.parseByte(value);
    }
}
