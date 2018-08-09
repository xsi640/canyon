package com.canyon.test.main

import com.canyon.boot.ApplicationContext
import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import com.canyon.inject.Named
import com.canyon.web.*

fun main(args: Array<String>) {
    ApplicationContext("com.canyon").run()
}

@Named("bb")
@Bean
class B : AA() {
    override fun test() {
        println("bbbbbbbbbbb")
    }

}

@Named("aa")
@Bean
class A : AA() {
    override fun test() {
        println("aaaaaaaaaaa")
    }
}

abstract class AA {
    abstract fun test()
}

class App : Boot() {

    @Named("bb")
    @Autowire
    var a: AA? = null

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