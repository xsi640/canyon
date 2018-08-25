package com.canyon.inject;

import com.canyon.scan.BaseDependentProperty;
import com.canyon.scan.ClassType;
import com.canyon.scan.SingleDependentProperty;

import java.util.ArrayList;
import java.util.List;

public class StandardBeanFactory implements BeanFactory {
    private InjectorContext injectorContext;

    @Override
    public <T> T createBean(ClassType<T> classType) throws InstantiationException, IllegalAccessException {
        if (classType.isSingle()) {
            if (injectorContext.getInjectedCached().containsKey(classType)) {
                //noinspection unchecked
                return (T) injectorContext.getInjectedCached().get(classType);
            } else {
                return buildBean(classType);
            }
        } else {
            return buildBean(classType);
        }
    }

    @Override
    public void setInjectContext(InjectorContext injectorContext) {
        this.injectorContext = injectorContext;
    }

    private <T> T buildBean(ClassType<T> classType) throws IllegalAccessException, InstantiationException {
        T instance = classType.getClazz().newInstance();
        if (!classType.getDependentPropertys().isEmpty()) {
            for (BaseDependentProperty prop : classType.getDependentPropertys()) {
                if (prop.getInjectProvider() != null) {
                    if (prop instanceof SingleDependentProperty) {
                        Object propInstance = this.createByProvider(classType, ((SingleDependentProperty) prop).getClassType(), prop.getInjectProvider());
                        prop.getField().set(instance, propInstance);
                    } else if (prop instanceof MultiDependentProperty) {
                        List<Object> lists = new ArrayList<>();
                        for (ClassType ct : ((MultiDependentProperty) prop).getClassTypes()) {
                            lists.add(this.createByProvider(classType, ct, prop.getInjectProvider()));
                        }
                        prop.getField().set(instance, lists);
                    }
                } else {
                    if (prop instanceof SingleDependentProperty) {
                        Object propInstance = this.createBean(((SingleDependentProperty) prop).getClassType());
                        prop.getField().set(instance, propInstance);
                    } else if (prop instanceof MultiDependentProperty) {
                        List<Object> lists = new ArrayList<>();
                        for (ClassType ct : ((MultiDependentProperty) prop).getClassTypes()) {
                            lists.add(this.createBean(ct));
                        }
                        prop.getField().set(instance, lists);
                    }
                }
            }
        }
        return instance;
    }

    private Object createByProvider(ClassType classType, ClassType fieldClassType, InjectProvider provider) {
        return provider.create(classType.getClazz(), fieldClassType.getClazz());
    }
}
