package com.canyon.test.main

import com.canyon.boot.ApplicationContext
import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import com.canyon.inject.Bean

fun main(args: Array<String>) {
    ApplicationContext("com.canyon").run()
}


@Bean
class A {
    fun test() {
        println("asdadad")
    }
}

class App : Boot {

    @Autowire
    var a: A? = null

    override fun run() {
        a!!.test()
    }

    override fun destory() {
    }

}