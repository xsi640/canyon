package com.canyon.web;

import com.canyon.inject.Config;
import com.typesafe.config.ConfigObject;

@Config(path = "web")
public class WebConfig {
    private int port = 8080;
    private String staticRoot;
    private boolean exceptionOutput = true;
    private String uploadTempDir = "";
    private ConfigObject errorPages;

    public WebConfig() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getStaticRoot() {
        return staticRoot;
    }

    public void setStaticRoot(String staticRoot) {
        this.staticRoot = staticRoot;
    }

    public boolean isExceptionOutput() {
        return exceptionOutput;
    }

    public void setExceptionOutput(boolean exceptionOutput) {
        this.exceptionOutput = exceptionOutput;
    }

    public String getUploadTempDir() {
        return uploadTempDir;
    }

    public void setUploadTempDir(String uploadTempDir) {
        this.uploadTempDir = uploadTempDir;
    }

    public ConfigObject getErrorPages() {
        return errorPages;
    }

    public void setErrorPages(ConfigObject errorPages) {
        this.errorPages = errorPages;
    }
}
