package com.canyon.web;

import com.canyon.inject.Bean;
import com.canyon.web.router.WebRouterParam;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

@Bean(singleton = true)
public class StandardWebParameterParser implements WebParameterParser {
    @Override
    public String parser(WebRouterParam webParam, RoutingContext rc) {
        switch (webParam.getFrom()) {
            case PATH:
            case QUERY:
                return rc.request().getParam(webParam.getName());
            case FORM:
                return rc.request().getFormAttribute(webParam.getName());
            case HEADER:
                return rc.request().getHeader(webParam.getName());
            case ENTITY:
                if (rc.getBody() == null || rc.getBody().length() == 0) {
                    return null;
                } else {
                    return rc.getBodyAsString();
                }
            case COOKIE:
                Cookie cookie = rc.getCookie(webParam.getName());
                if (cookie == null)
                    return null;
                else
                    return cookie.getValue();
            default:
                String value = rc.request().getParam(webParam.getName());
                if (value == null) {
                    value = rc.request().getFormAttribute(webParam.getName());
                }
                if (value == null) {
                    value = rc.request().getHeader(webParam.getName());
                }
                if (value == null) {
                    Cookie cookie0 = rc.getCookie(webParam.getName());
                    if (cookie0 != null) {
                        value = cookie0.getValue();
                    }
                }
                if (value != null) {
                    if (rc.getBody() != null && rc.getBody().length() > 0) {
                        value = rc.getBodyAsString();
                    }
                }
                return value;
        }
    }
}
