package org.jboss.aerogear.controller;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RouteDescriptor implements RouteBuilder.OnMethods, RouteBuilder.TargetEndpoint {
    private final String path;
    private Method targetMethod;
    private Object[] args;

    public RouteDescriptor(String path) {
        this.path = path;
    }

    @Override
    public RouteBuilder.TargetEndpoint on(RequestMethod... methods) {
        return this;
    }

    @Override
    public <T> T to(Class<T> clazz) {
        try {
            Object o = Enhancer.create(clazz, new MyMethodInterceptor(this));
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class MyMethodInterceptor implements MethodInterceptor {
        private final RouteDescriptor routeDescriptor;

        public MyMethodInterceptor(RouteDescriptor routeDescriptor) {
            this.routeDescriptor = routeDescriptor;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            this.routeDescriptor.targetMethod = method;
            this.routeDescriptor.args = args;
            return null;
        }
    }

    @Override
    public String toString() {
        return "RouteDescriptor{" +
                "path='" + path + '\'' +
                ", targetMethod=" + targetMethod +
                ", args=" + (args == null ? null : Arrays.asList(args)) +
                '}';
    }
}
