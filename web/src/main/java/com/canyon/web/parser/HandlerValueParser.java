package com.canyon.web.parser;


import com.canyon.web.router.WebRouterParam;

public interface HandlerValueParser {
    Object parser(WebRouterParam webParam, String value);
}
