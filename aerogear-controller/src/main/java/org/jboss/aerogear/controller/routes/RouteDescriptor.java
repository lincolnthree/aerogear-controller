package org.jboss.aerogear.controller.routes;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jboss.aerogear.controller.RequestMethod;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RouteDescriptor implements RouteBuilder.OnMethods, RouteBuilder.TargetEndpoint {
    private final String path;
    private Method targetMethod;
    private Object[] args;
    private RequestMethod[] methods;
    private Class<?> targetClass;

    public RouteDescriptor(String path) {
        this.path = path;
    }

    @Override
    public RouteBuilder.TargetEndpoint on(RequestMethod... methods) {
        this.methods = methods;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T to(Class<T> clazz) {
        this.targetClass = clazz;
        try {
            Object o = Enhancer.create(clazz, new RouteMethodInterceptor(this));
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return path;
    }

    public RequestMethod[] getMethods() {
        return methods;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    private static class RouteMethodInterceptor implements MethodInterceptor {
        private final RouteDescriptor routeDescriptor;

        public RouteMethodInterceptor(RouteDescriptor routeDescriptor) {
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
        return "RouteDescriptor{" + "path='" + path + '\'' + ", targetMethod=" + targetMethod + ", args="
                + (args == null ? null : Arrays.asList(args)) + '}';
    }
}
