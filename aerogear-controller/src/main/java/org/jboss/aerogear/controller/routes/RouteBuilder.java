package org.jboss.aerogear.controller.routes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jboss.aerogear.controller.RequestMethod;

public class RouteBuilder implements Route {


    public static interface OnMethods {
        TargetEndpoint on(RequestMethod... methods);
    }

    public static interface TargetEndpoint {
        <T> T to(Class<T> clazz);
    }

    private RouteDescriptor routeDescriptor;

    public OnMethods from(String path) {
        return routeDescriptor = new RouteDescriptor(path);
    }

    @Override
    public String toString() {
        return "RouteBuilderImpl{" +
                "routeDescriptor=" + routeDescriptor +
                '}';
    }

    @Override
    public Set<RequestMethod> getMethods() {
        return new HashSet<RequestMethod>(Arrays.asList(routeDescriptor.getMethods()));
    }

    @Override
    public String getPath() {
        return routeDescriptor.getPath();
    }

    @Override
    public Method getTargetMethod() {
        return routeDescriptor.getTargetMethod();
    }

    @Override
    public Class<?> getTargetClass() {
        return routeDescriptor.getTargetClass();
    }
}
