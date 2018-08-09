package com.canyon.web

import com.canyon.boot.Boot
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

class WebSiteBoot : Boot() {

    private val vertx: Vertx = Vertx.vertx()

    override fun run() {
        val routers = mutableListOf<WebRouter>()
        val controllers = super.injectorContext.getBeansFromAnnotation(Controller::class)
        for (controller in controllers) {
            val rs = toWebRouter(controller)
            if (rs.isNotEmpty()) {
                routers.addAll(rs)
            }
        }

        val router = Router.router(vertx)
        for (r in routers) {
            if (r.method.isNotEmpty()) {
                for (method in r.method) {
                    router.route(method.toMethod(), r.path)
                            .consumes(r.consumes.toText())
                            .produces(r.produces.toText())
                            .handler { rc ->
                                RequestHandler(r).handle(rc)
                            }
                }
            }
        }
        vertx.createHttpServer().requestHandler(router::accept).listen(8080)
    }

    override fun destory() {
    }
}

data class WebRouter(
        val path: String,
        val method: List<Method>,
        val consumes: MIME,
        val produces: MIME,
        val webParam: List<WebRouterParam>,
        val function: KFunction<*>,
        val controller: Any
)

data class WebRouterParam(
        val name: String,
        val from: From,
        val default: String,
        val kType: KType
)

fun Method.toMethod(): HttpMethod {
    return when (this) {
        Method.GET -> HttpMethod.GET
        Method.POST -> HttpMethod.POST
        Method.HEAD -> HttpMethod.HEAD
        Method.PUT -> HttpMethod.PUT
        Method.DELETE -> HttpMethod.DELETE
        else -> throw IllegalArgumentException("Can't support The method.")
    }
}

fun toWebRouter(c: Any): List<WebRouter> {
    val result = mutableListOf<WebRouter>()
    val kClass = c::class
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
                kFun.parameters.forEach { kParam ->
                    if (kParam.name == null)
                        return@forEach
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
                    c))
        }
    }
    return result
}