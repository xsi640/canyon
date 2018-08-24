package com.canyon.inject;

import com.canyon.inject.exceptions.NotfoundDependencies;
import com.canyon.scan.ClassType;

import java.util.Collection;

public interface DependenciesProcessor {
    void process(Collection<ClassType> classes) throws IllegalAccessException, InstantiationException, NotfoundDependencies;
}