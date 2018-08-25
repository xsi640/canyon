package com.canyon.inject;

import com.canyon.scan.ClassType;

public interface BeanFactory {
    <T> T createBean(ClassType<T> classType) throws InstantiationException, IllegalAccessException;

    void setInjectContext(InjectorContext injectorContext);
}
