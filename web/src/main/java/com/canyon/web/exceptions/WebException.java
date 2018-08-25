package com.canyon.web.exceptions;

public class WebException extends Exception {
    private int statusCode = 200;

    public WebException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
