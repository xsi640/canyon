package com.canyon.web

import com.canyon.inject.Bean
import io.vertx.ext.web.RoutingContext
import java.nio.charset.StandardCharsets

interface WebParameterParser {
    fun parser(webParam: WebRouterParam, rc: RoutingContext): String?
}

@Bean(singleton = true)
class WebParameterParserImpl : WebParameterParser {
    override fun parser(webParam: WebRouterParam, rc: RoutingContext): String? {
        return when (webParam.from) {
            From.PATH, From.QUERY -> {
                rc.request().getParam(webParam.name)
            }
            From.FORM -> {
                rc.request().getFormAttribute(webParam.name)
            }
            From.HEADER -> rc.request().getHeader(webParam.name)
            From.ENTITY -> {
                if (rc.body.bytes.isEmpty())
                    null
                else
                    String(rc.body.bytes, StandardCharsets.UTF_8)
            }
            From.COOKIE -> {
                rc.getCookie(webParam.name)?.value
            }
            else -> {
                var v = rc.request().getParam(webParam.name)
                if (v == null)
                    v = rc.request().getFormAttribute(webParam.name)
                if (v == null)
                    v = rc.request().getHeader(webParam.name)
                if (v == null) {
                    val cookie = rc.getCookie(webParam.name)
                    if (cookie != null)
                        v = cookie.value
                }
                if (v == null) {
                    if (!rc.body.bytes.isEmpty()) {
                        v = String(rc.body.bytes, StandardCharsets.UTF_8)
                    }
                }
                v
            }
        }
    }
}