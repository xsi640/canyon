package com.canyon.scan;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class StandardClassScanner implements ClassScanner {

    private ScanResult scanResult;

    public StandardClassScanner(String[] packageNames) {
        FastClasspathScanner scanner = new FastClasspathScanner(packageNames);
        this.scanResult = scanner.scan(Runtime.getRuntime().availableProcessors());
    }

    private static ClassType apply(String name) {
        try {
            return ClassType.toClassType(Class.forName(name));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClassType> scanByAnnotation(String[] packageNames, Class<? extends Annotation> annotationType) {
        List<String> names = scanResult.getNamesOfClassesWithAnnotation(annotationType);
        return names.parallelStream().map(StandardClassScanner::apply).collect(Collectors.toList());
    }

    @Override
    public List<ClassType> scanBySuperClass(String[] packageNames, Class<?> superClass) {
        List<String> names = scanResult.getNamesOfSubclassesOf(superClass);
        return names.parallelStream().map(StandardClassScanner::apply).collect(Collectors.toList());
    }

    @Override
    public List<ClassType> scanByInterface(String[] packageNames, Class<?> interfaceClass) {
        List<String> names = scanResult.getNamesOfClassesImplementing(interfaceClass);
        return names.parallelStream().map(StandardClassScanner::apply).collect(Collectors.toList());
    }
}
