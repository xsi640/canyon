package com.canyon.web;

import com.canyon.web.router.WebRouterParam;
import io.vertx.ext.web.RoutingContext;

public interface WebParameterParser {
    String parser(WebRouterParam webParam, RoutingContext rc);
}
