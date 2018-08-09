package com.canyon.web

import com.canyon.inject.Config

@Config("/web")
class WebConfig(
        val port: Int
)