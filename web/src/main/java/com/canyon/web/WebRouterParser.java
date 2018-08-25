package com.canyon.web;

import com.canyon.web.router.WebRouter;

import java.util.List;

public interface WebRouterParser {
    List<WebRouter> parser(Object controller);
}
