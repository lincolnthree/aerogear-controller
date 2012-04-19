package org.jboss.aerogear.controller;

import java.lang.reflect.Method;
import java.util.Set;

public interface Route {

    public Set<RequestMethod> getMethods();

    public String getPath();

    Method getTargetMethod();

    Class<?> getTargetClass();
}
