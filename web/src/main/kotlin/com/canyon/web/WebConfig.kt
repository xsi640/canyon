package com.canyon.web

import com.canyon.inject.Config
import org.omg.CORBA.Object

@Config("web")
class WebConfig {
    var port: Int = 8080
    var staticRoot: String = ""
    var exceptionOutput: Boolean = true
}