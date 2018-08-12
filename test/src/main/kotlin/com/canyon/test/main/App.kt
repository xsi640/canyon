package com.canyon.test.main

import com.canyon.boot.ApplicationContext
import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import com.canyon.store.DataSource

fun main(args: Array<String>) {
    ApplicationContext("com.canyon").run()
}

class StoreTest : Boot() {

    @Autowire
    var datasource: DataSource? = null

    override fun run() {
        datasource!!.open()

    }

    override fun destory() {
    }

}

//data class Person(
//        val name: String,
//        val age: Int
//)
//
//@Controller
//@Path("/aaa")
//class TestController {
//    @Path("/hello")
//    @WebMethod(HttpMethod.GET, responseMediaType = MediaType.APPLICATION_JSON, requestMediaType = MediaType.ALL)
//    fun hello(request: HttpServerRequest): Person {
//        return Person("11", 2)
//    }
//
//    @Path("/upload")
//    @WebMethod(HttpMethod.POST, requestMediaType = MediaType.ALL)
//    fun upload(@WebParam(name = "file1") files: List<FileUpload>) {
//        files.forEach {
//            println(it.name())
//            println(it.fileName())
//        }
//    }
//}