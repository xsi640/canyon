package com.canyon.test.main

import com.canyon.boot.ApplicationContext
import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import com.canyon.web.*

fun main(args: Array<String>) {
    ApplicationContext("com.canyon").run()
}


@Bean
class A {
    fun test() {
        println("asdadad")
    }
}

class App : Boot() {

    @Autowire
    var a: A? = null

    override fun run() {
        a!!.test()
    }

    override fun destory() {
    }

}

@Controller
@Path("/aaa")
class TestController {
    @Path("/hello")
    @WebMethod(Method.GET, consumes = MediaType.APPLICATION_JSON)
    fun hello(name: String): String {
        return "nihao, $name"
    }
}