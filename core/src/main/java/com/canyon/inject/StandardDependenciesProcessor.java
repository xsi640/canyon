package com.canyon.inject;

import com.canyon.commons.CollectionUtils;
import com.canyon.commons.StringUtils;
import com.canyon.inject.exceptions.NotfoundDependencies;
import com.canyon.scan.BaseDependentProperty;
import com.canyon.scan.ClassType;
import com.canyon.scan.SingleDependentProperty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StandardDependenciesProcessor implements DependenciesProcessor {

    private List<InjectProvider> providers = new ArrayList<>();

    @Override
    public void process(Collection<ClassType> classes) throws IllegalAccessException, InstantiationException, NotfoundDependencies {
        for (ClassType classType : classes) {
            Class<?>[] interfaces = classType.getClazz().getInterfaces();
            for (Class<?> interface0 : interfaces) {
                if (interface0.equals(InjectProvider.class)) {
                    providers.add((InjectProvider) classType.getClazz().newInstance());
                    break;
                }
            }
        }

        for (ClassType classType : classes) {
            classType.setDependentPropertys(grunt(classType, classes));
        }
    }

    private List<BaseDependentProperty> grunt(ClassType classType, Collection<ClassType> classes) throws NotfoundDependencies {
        List<BaseDependentProperty> result = new ArrayList<>();
        for (Field field : classType.getClazz().getDeclaredFields()) {
            if (field.getAnnotation(Autowire.class) != null) {
                field.setAccessible(true);
                Named named = field.getAnnotation(Named.class);
                String name = named == null ? "" : named.value();
                if (field.getType().isArray() ||
                        field.getType().isAssignableFrom(List.class)) {
                    result.add(new MultiDependentProperty("", field, findProvider(field), findClassTypeList(field, name, classes)));
                } else {
                    result.add(new SingleDependentProperty(name, field, findProvider(field), findClassType(field, name, classes)));
                }
            }
        }
        return result;
    }

    private List<ClassType> findClassTypeList(Field field, String name, Collection<ClassType> classes) throws NotfoundDependencies {
        List<ClassType> result = new ArrayList<>();
        for (ClassType classType : classes) {
            if (isChildren(field, classType.getClazz())) {
                if (StringUtils.isNotEmpty(name) && name.equals(classType.getName())) {
                    result.add(classType);
                } else if (StringUtils.isEmpty(name)) {
                    result.add(classType);
                }
            }
        }
        if (result.isEmpty()) {
            throw new NotfoundDependencies(StringUtils.format("Not found Inject Class from field:{0} in class:{1}.", field.getName(), field.getType()));
        }
        return result;
    }

    private ClassType findClassType(Field field, String name, Collection<ClassType> classes) {
        for (ClassType classType : classes) {
            if (isChildren(field, classType.getClazz())) {
                if (StringUtils.isNotEmpty(name) && name.equals(classType.getName())) {
                    return classType;
                } else if (StringUtils.isEmpty(name)) {
                    return classType;
                }
            }
        }

        return ClassType.toClassType(field.getType());
    }

    private InjectProvider findProvider(Field field) {
        return CollectionUtils.findOne(providers, provider -> {
            return provider.isMatch(field.getType());
        });
    }

    private boolean isChildren(Field field, Class<?> clazz) {
        if (field.getType().equals(clazz))
            return true;
        if (clazz.getSuperclass().equals(clazz))
            return true;
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (type.equals(clazz)) {
                return true;
            }else if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                ParameterizedType parameterizedType1 = (ParameterizedType) wildcardType.getUpperBounds()[0];
                Type type1 = parameterizedType1.getRawType();
                if (clazz.getSuperclass().equals(type1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
