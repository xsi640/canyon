package com.canyon.web

import com.canyon.inject.Bean
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

interface WebRouterParser {
    fun parser(controller: Any): List<WebRouter>
}

@Bean
class WebRouterParserImpl : WebRouterParser {
    override fun parser(controller: Any): List<WebRouter> {
        val result = mutableListOf<WebRouter>()
        val kClass = controller::class
        val path = kClass.findAnnotation<Path>()
        kClass.functions.forEach { kFun ->
            val webMethod = kFun.findAnnotation<WebMethod>()
            if (webMethod != null) {
                val methodPath = kFun.findAnnotation<Path>()
                val params = mutableListOf<WebRouterParam>()
                var strPath = path?.path ?: ""
                if (methodPath != null) {
                    strPath += methodPath.path
                }
                if (strPath.isEmpty())
                    return@forEach

                if (kFun.parameters.isNotEmpty()) {
                    for (kParam in kFun.parameters) {
                        if (kParam.name == null)
                            continue
                        val webParam = kParam.findAnnotation<WebParam>()
                        if (webParam == null) {
                            params.add(WebRouterParam(kParam.name!!, From.ANY, "", kParam.type))
                        } else {
                            params.add(WebRouterParam(webParam.name, webParam.from, webParam.default, kParam.type))
                        }
                    }
                }
                result.add(WebRouter(strPath,
                        webMethod.method.toList(),
                        webMethod.consumes,
                        webMethod.produces,
                        params,
                        kFun,
                        controller))
            }
        }
        return result
    }

}