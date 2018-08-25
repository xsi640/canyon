package com.canyon.web.router;

import com.canyon.web.HttpMethod;
import com.canyon.web.MediaType;

import java.lang.reflect.Method;
import java.util.List;

public class WebRouter {
    private String path;
    private List<HttpMethod> methods;
    private String request;
    private String response;
    private List<WebRouterParam> webParams;
    private Method method;
    private Object controller;

    public WebRouter(String path, List<HttpMethod> methods, String request, String response, List<WebRouterParam> webParams, Method method, Object controller) {
        this.path = path;
        this.methods = methods;
        this.request = request;
        this.response = response;
        this.webParams = webParams;
        this.method = method;
        this.controller = controller;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<HttpMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<HttpMethod> methods) {
        this.methods = methods;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<WebRouterParam> getWebParams() {
        return webParams;
    }

    public void setWebParams(List<WebRouterParam> webParams) {
        this.webParams = webParams;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
