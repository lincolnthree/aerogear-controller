package org.jboss.aerogear.controller;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RouteDescriptorImpl implements RouteBuilder.RouteDescriptor {
    private final String path;

    public RouteDescriptorImpl(String path) {
        this.path = path;
    }

    @Override
    public RouteBuilder.RouteDescriptor on(String... methods) {
        return this;
    }

    @Override
    public <T> T to(Class<T> clazz) {
        try {
            Object o = Enhancer.create(clazz, new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    System.out.println(method);
                    System.out.println(Arrays.toString(args));
                    return null;
                }
            });
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
