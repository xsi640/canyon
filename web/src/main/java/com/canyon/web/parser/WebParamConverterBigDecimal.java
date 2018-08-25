package com.canyon.web.parser;

import com.canyon.inject.Bean;

import java.math.BigDecimal;

@Bean
public class WebParamConverterBigDecimal implements WebParamConverter<BigDecimal> {
    @Override
    public BigDecimal convert(String value) {
        return new BigDecimal(value);
    }
}
