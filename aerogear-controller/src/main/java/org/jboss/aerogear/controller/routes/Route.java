package org.jboss.aerogear.controller.routes;

import java.lang.reflect.Method;
import java.util.Set;

import org.jboss.aerogear.controller.RequestMethod;

public interface Route {

    public Set<RequestMethod> getMethods();

    public String getPath();

    Method getTargetMethod();

    Class<?> getTargetClass();
}
