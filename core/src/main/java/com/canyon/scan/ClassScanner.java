package com.canyon.scan;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器，从package中扫描匹配的Class
 */
public interface ClassScanner {
    List<ClassType> scanByAnnotation(String[] packageNames, Class<? extends Annotation> annotationType);

    List<ClassType> scanBySuperClass(String[] packageNames, Class<?> superClass);

    List<ClassType> scanByInterface(String[] packageNames, Class<?> interfaceClass);
}
