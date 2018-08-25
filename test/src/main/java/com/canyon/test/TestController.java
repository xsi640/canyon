package com.canyon.test;

import com.canyon.commons.StringUtils;
import com.canyon.web.*;

@Controller
@Path(path = "/aaa")
public class TestController {


    @WebMethod(method = HttpMethod.GET)
    public String test(@WebParam(name = "name") String name) {
        return "hello " + name;
    }
}
