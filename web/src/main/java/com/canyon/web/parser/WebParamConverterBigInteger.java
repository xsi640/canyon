package com.canyon.web.parser;

import com.canyon.inject.Bean;
import java.math.BigInteger;

@Bean
public class WebParamConverterBigInteger implements WebParamConverter<BigInteger> {
    @Override
    public BigInteger convert(String value) {
        return new BigInteger(value);
    }
}
