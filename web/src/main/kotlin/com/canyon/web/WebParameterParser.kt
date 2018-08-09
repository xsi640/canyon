package com.canyon.web

import com.canyon.inject.Bean
import io.vertx.ext.web.RoutingContext
import java.nio.charset.StandardCharsets

interface WebParameterParser {
    fun parser(webParam: WebRouterParam, rc: RoutingContext): String
}

@Bean(singleton = true)
class WebParameterParserImpl : WebParameterParser {
    override fun parser(webParam: WebRouterParam, rc: RoutingContext): String {
        return when (webParam.from) {
            From.PATH, From.QUERY -> rc.request().getParam(webParam.name)
            From.HEADER -> rc.request().getHeader(webParam.name)
            From.ENTITY -> {
                String(rc.body.bytes, StandardCharsets.UTF_8)
            }
            From.COOKIE -> rc.getCookie(webParam.name).value
            else -> {
                rc.request().getParam(webParam.name)
            }
        }
    }
}