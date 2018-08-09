package com.canyon.web

import com.canyon.inject.Bean
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

interface WebParamConverter<T : Any> {
    fun convert(value: String): T
}

@Bean
class WebParamConverterBoolean : WebParamConverter<Boolean> {
    override fun convert(value: String): Boolean {
        return value.toBoolean()
    }
}

@Bean
class WebParamConverterByte : WebParamConverter<Byte> {
    override fun convert(value: String): Byte {
        return value.toByte()
    }
}

@Bean
class WebParamConverterShort : WebParamConverter<Short> {
    override fun convert(value: String): Short {
        return value.toShort()
    }
}

@Bean
class WebParamConverterInt : WebParamConverter<Int> {
    override fun convert(value: String): Int {
        return value.toInt()
    }
}

@Bean
class WebParamConverterLong : WebParamConverter<Long> {
    override fun convert(value: String): Long {
        return value.toLong()
    }
}

@Bean
class WebParamConverterFloat : WebParamConverter<Float> {
    override fun convert(value: String): Float {
        return value.toFloat()
    }
}

@Bean
class WebParamConverterDouble : WebParamConverter<Double> {
    override fun convert(value: String): Double {
        return value.toDouble()
    }
}

@Bean
class WebParamConverterBigInteger : WebParamConverter<BigInteger> {
    override fun convert(value: String): BigInteger {
        return value.toBigInteger()
    }
}

@Bean
class WebParamConverterBigDecimal : WebParamConverter<BigDecimal> {
    override fun convert(value: String): BigDecimal {
        return value.toBigDecimal()
    }
}

@Bean
class WebParamConverterDate : WebParamConverter<Date> {
    override fun convert(value: String): Date {
        return Date(value.toLong())
    }

}