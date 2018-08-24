package com.canyon.inject;

import com.canyon.inject.exceptions.InitializeException;
import com.canyon.inject.exceptions.NotFoundBeanException;
import com.canyon.scan.ClassType;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 依赖注入上下文
 */
public interface InjectorContext {

    Map<ClassType, Object> getInjectedCached();

    void excludedClass(Class<?> clazz);

    void registAnnotation(Class<? extends Annotation> clazz);

    void registSuperclass(Class<?> clazz);

    void registInterface(Class<?> clazz);

    void addBean(Object obj);

    void initialize();

    <T> T getBean(Class<T> clazz) throws InitializeException, NotFoundBeanException, IllegalAccessException, InstantiationException;

    <T> List<T> getBeanFromSuper(Class<T> clazz, String name) throws InitializeException, NotFoundBeanException, InstantiationException, IllegalAccessException;

    <T> List<T> getBeansFromAnnotation(Class<? extends Annotation> clazz, String name) throws InitializeException, NotFoundBeanException, InstantiationException, IllegalAccessException;

    <T> List<T> getBeansFromInterface(Class<T> clazz, String name) throws InitializeException, NotFoundBeanException, InstantiationException, IllegalAccessException;
}