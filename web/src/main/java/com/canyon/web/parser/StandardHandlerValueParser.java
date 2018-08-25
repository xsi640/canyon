package com.canyon.web.parser;

import com.canyon.commons.CollectionUtils;
import com.canyon.commons.JsonUtils;
import com.canyon.commons.StringUtils;
import com.canyon.inject.Bean;
import com.canyon.web.From;
import com.canyon.web.router.WebRouterParam;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Bean(singleton = true)
public class StandardHandlerValueParser implements HandlerValueParser {

    private List<WebParamConverter<?>> parsers;

    private ConcurrentHashMap<Class<?>, WebParamConverter<?>> map = new ConcurrentHashMap<>();

    @Override
    public Object parser(WebRouterParam webParam, String value) {
        String v = value;
        if (value == null && StringUtils.isNotEmpty(webParam.getDefaultValue())) {
            v = webParam.getDefaultValue();
        }
        if (webParam.getClazz().equals(String.class)) {
            return v;
        }
        if (webParam.getFrom().equals(From.ENTITY)) {
            return JsonUtils.parse(v, webParam.getClazz());
        } else {
            WebParamConverter webParamConverter = map.computeIfAbsent(webParam.getClazz(), type -> CollectionUtils.findOne(parsers, converter -> {
                Type t = converter.getClass().getGenericInterfaces()[0];
                if (((ParameterizedType) t).getActualTypeArguments()[0].equals(type)) {
                    return true;
                }
                return false;
            }));
            return webParamConverter.convert(v);
        }
    }
}
