package com.canyon.web;

import com.canyon.core.annotation.Description;

public enum MediaType {
    @Description(description = "*/*")
    ALL,
    @Description(description = "application/x-www-form-urlencoded")
    APPLICATION_FORM_URLENCODED,
    @Description(description = "application/json")
    APPLICATION_JSON,
    @Description(description = "text/plain;charset=utf-8")
    TEXT_PLAIN,
    @Description(description = "text/html;charset=utf-8")
    TEXT_HTML,
}
