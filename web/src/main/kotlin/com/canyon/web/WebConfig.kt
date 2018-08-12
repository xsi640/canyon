package com.canyon.web

import com.canyon.inject.Config
import com.typesafe.config.ConfigObject

@Config("web")
class WebConfig {
    var port: Int = 8080
    var staticRoot: String = ""
    var exceptionOutput: Boolean = true
    var errorPages: ConfigObject? = null
}