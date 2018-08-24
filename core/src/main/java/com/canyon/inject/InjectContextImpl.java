package com.canyon.inject;

import com.canyon.commons.CollectionUtils;
import com.canyon.commons.StringUtils;
import com.canyon.inject.exceptions.InitializeException;
import com.canyon.inject.exceptions.NotFoundBeanException;
import com.canyon.inject.exceptions.NotfoundDependencies;
import com.canyon.scan.ClassScanner;
import com.canyon.scan.ClassType;

import java.lang.annotation.Annotation;
import java.util.*;

public class InjectContextImpl implements InjectorContext {

    private Map<ClassType, Object> injectedCached = new HashMap<>();
    private Map<Class<?>, ClassType> classTypeCached = new HashMap<>();

    private List<Class<? extends Annotation>> regAnnotations = new ArrayList<>();
    private List<Class<?>> regSuperClasses = new ArrayList<>();
    private List<Class<?>> regInterfaceClasses = new ArrayList<>();
    private List<Class<?>> excludedClasses = new ArrayList<>();

    private ClassScanner classScanner;
    private DependenciesProcessor dependenciesProcessor;
    private BeanFactory beanFactory;

    private String[] packages;
    private boolean initialized = false;

    public InjectContextImpl(ClassScanner classScanner, DependenciesProcessor dependenciesProcessor, BeanFactory beanFactory, String... packages) {
        this.classScanner = classScanner;
        this.dependenciesProcessor = dependenciesProcessor;
        this.beanFactory = beanFactory;
        this.packages = packages;
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws InitializeException, NotFoundBeanException, IllegalAccessException, InstantiationException {
        if (!initialized) {
            throw new InitializeException("InjectorContext initializing...");
        }
        if (!classTypeCached.containsKey(clazz)) {
            throw new NotFoundBeanException(StringUtils.format("Not found Bean class{}.", clazz));
        } else {
            ClassType classType = classTypeCached.get(clazz);
            //noinspection unchecked
            return (T) beanFactory.createBean(classType);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public <T> List<T> getBeanFromSuper(Class<T> clazz, String name) throws InitializeException, NotFoundBeanException, InstantiationException, IllegalAccessException {
        if (!initialized) {
            throw new InitializeException("InjectorContext initializing...");
        }
        List<T> result = new ArrayList<>();
        for (Class<?> c : classTypeCached.keySet()) {
            if (c.getSuperclass().equals(clazz)) {
                if (StringUtils.isEmpty(name)) {
                    //noinspection unchecked
                    result.add((T) this.getBean(c));
                } else {
                    ClassType classType = classTypeCached.get(clazz);
                    if (classType != null && name.equals(classType.getName())) {
                        //noinspection unchecked
                        result.add((T) getBean(c));
                    }
                }
            }
        }
        return result;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public <T> List<T> getBeansFromAnnotation(Class<? extends Annotation> clazz, String name) throws InitializeException, NotFoundBeanException, InstantiationException, IllegalAccessException {
        if (!initialized) {
            throw new InitializeException("InjectorContext initializing...");
        }
        List<T> result = new ArrayList<>();
        for (Class<?> c : classTypeCached.keySet()) {
            if (c.getAnnotation(clazz) != null) {
                if (StringUtils.isEmpty(name)) {
                    //noinspection unchecked
                    result.add((T) this.getBean(c));
                } else {
                    ClassType classType = classTypeCached.get(clazz);
                    if (classType != null && name.equals(classType.getName())) {
                        //noinspection unchecked
                        result.add((T) getBean(c));
                    }
                }
            }
        }
        return result;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public <T> List<T> getBeansFromInterface(Class<T> clazz, String name) throws InitializeException, NotFoundBeanException, InstantiationException, IllegalAccessException {
        if (!initialized) {
            throw new InitializeException("InjectorContext initializing...");
        }
        List<T> result = new ArrayList<>();
        for (Class<?> c : classTypeCached.keySet()) {
            for (Class<?> c0 : c.getInterfaces()) {
                if (c0.equals(clazz)) {
                    if (StringUtils.isEmpty(name)) {
                        //noinspection unchecked
                        result.add((T) this.getBean(c));
                    } else {
                        ClassType classType = classTypeCached.get(clazz);
                        if (classType != null && name.equals(classType.getName())) {
                            //noinspection unchecked
                            result.add((T) getBean(c));
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Map<ClassType, Object> getInjectedCached() {
        return this.injectedCached;
    }

    @Override
    public void excludedClass(Class<?> clazz) {
        excludedClasses.add(clazz);
    }

    @Override
    public void registAnnotation(Class<? extends Annotation> clazz) {
        regAnnotations.add(clazz);
    }

    @Override
    public void registSuperclass(Class<?> clazz) {
        regSuperClasses.add(clazz);
    }

    @Override
    public void registInterface(Class<?> clazz) {
        regInterfaceClasses.add(clazz);
    }

    @Override
    public void addBean(Object obj) {
        ClassType classType = ClassType.toClassType(obj.getClass());
        classTypeCached.put(obj.getClass(), classType);
        injectedCached.put(classType, obj);
    }

    @Override
    public void initialize() {
        classTypeCached.clear();

        Set<ClassType> set = new HashSet<>();
        for (Class<? extends Annotation> clazz : regAnnotations) {
            set.addAll(classScanner.scanByAnnotation(packages, clazz));
        }
        for (Class<?> clazz : regSuperClasses) {
            set.addAll(classScanner.scanBySuperClass(packages, clazz));
        }
        for (Class<?> clazz : regInterfaceClasses) {
            set.addAll(classScanner.scanByInterface(packages, clazz));
        }

        try {
            dependenciesProcessor.process(set);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NotfoundDependencies notfoundDependencies) {
            notfoundDependencies.printStackTrace();
        }

        for (ClassType classType : set) {
            if (CollectionUtils.findOne(excludedClasses, clazz -> classType.getClazz().equals(clazz)) == null) {
                classTypeCached.put(classType.getClazz(), classType);
            }
        }

        initialized = true;
    }
}
