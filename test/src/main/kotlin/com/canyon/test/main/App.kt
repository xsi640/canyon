package com.canyon.test.main

import com.canyon.boot.ApplicationContext
import com.canyon.web.*
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.FileUpload

fun main(args: Array<String>) {
    ApplicationContext("com.canyon").run()
}

data class Person(
        val name: String,
        val age: Int
)

@Controller
@Path("/aaa")
class TestController {
    @Path("/hello")
    @WebMethod(Method.GET, responseMediaType = MediaType.APPLICATION_JSON, requestMediaType = MediaType.ALL)
    fun hello(request: HttpServerRequest): Person {
        return Person("11", 2)
    }

    @Path("/upload")
    @WebMethod(Method.POST, requestMediaType = MediaType.ALL)
    fun upload(@WebParam(name = "file1") files: List<FileUpload>) {
        files.forEach {
            println(it.name())
            println(it.fileName())
        }
    }
}