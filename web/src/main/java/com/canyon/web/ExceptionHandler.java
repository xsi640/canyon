package com.canyon.web;

import com.canyon.inject.Autowire;
import com.canyon.inject.Bean;
import com.canyon.web.exceptions.WebException;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Bean
public class ExceptionHandler implements Handler<RoutingContext> {

    @Autowire
    private WebConfig config;
    @Autowire
    private Logger logger;

    @Override
    public void handle(RoutingContext rc) {
        if (rc.failure() instanceof WebException) {
            WebException ex = (WebException) rc.failure();
            rc.response().setStatusCode(ex.getStatusCode());
        } else {
            rc.response().setStatusCode(500);
        }

        if (config.isExceptionOutput()) {
            StringWriter sw = null;
            try {
                sw = new StringWriter();
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(sw);
                    rc.failure().printStackTrace(pw);
                    pw.flush();
                    rc.response().end(sw.toString());
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                } finally {
                    if (pw != null) {
                        pw.close();
                    }
                }
            } catch (Exception ex1) {
                logger.error(ex1.getMessage(), ex1);
            } finally {
                if (sw != null) {
                    try {
                        sw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            int statusCode = rc.response().getStatusCode();
            if (config.getErrorPages().containsKey(statusCode)) {
                rc.response().setStatusCode(302);
                rc.response().putHeader("content-type", MediaType.TEXT_HTML);
                rc.response().putHeader("Location", config.getErrorPages().get(statusCode).unwrapped().toString());
                rc.response().end();
            } else {
                rc.next();
            }
        }
    }
}
