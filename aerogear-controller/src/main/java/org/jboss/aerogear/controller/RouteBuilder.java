package org.jboss.aerogear.controller;

public interface RouteBuilder {

    RouteDescriptor from(String path);

    public static interface RouteDescriptor {
        RouteDescriptor on(String...methods);

        <T> T to(Class<T> clazz);
    }
}
