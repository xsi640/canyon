package com.canyon.web;

import com.canyon.commons.StringUtils;

public class WebRouterUtils {
    public static io.vertx.core.http.HttpMethod toMethod(HttpMethod method) {
        switch (method) {
            case GET:
                return io.vertx.core.http.HttpMethod.GET;
            case PUT:
                return io.vertx.core.http.HttpMethod.PUT;
            case HEAD:
                return io.vertx.core.http.HttpMethod.HEAD;
            case POST:
                return io.vertx.core.http.HttpMethod.POST;
            case DELETE:
                return io.vertx.core.http.HttpMethod.DELETE;
            default:
                throw new IllegalArgumentException(StringUtils.format("Can't support {0} method.", method));
        }
    }
}
