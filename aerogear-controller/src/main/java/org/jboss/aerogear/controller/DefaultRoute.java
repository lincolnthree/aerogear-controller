package org.jboss.aerogear.controller;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DefaultRoute implements Route {
    private final String path;
    private final Class<?> targetClass;
    private final Method targetMethod;
    private Set<RequestMethod> methods;

    public DefaultRoute(String path, RequestMethod[] methods, Class<?> targetClass, Method targetMethod) {
        this.path = path;
        this.methods = new HashSet<RequestMethod>(Arrays.asList(methods));
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
    }

    @Override
    public Set<RequestMethod> getMethods() {
        return methods;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

    @Override
    public Method getTargetMethod() {
        return targetMethod;
    }
}
