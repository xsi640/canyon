package com.canyon.test.main

import com.canyon.boot.ApplicationContext
import com.canyon.web.*

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
    @WebMethod(Method.GET, responseMediaType = MediaType.APPLICATION_JSON, requestMediaType = MediaType.APPLICATION_FORM_URLENCODED)
    fun hello(@WebParam(from = From.QUERY) id: Int,
              @WebParam(from = From.QUERY) p: Person): Person {
        return Person(p.name + "1", p.age + 1 + id)
    }
}