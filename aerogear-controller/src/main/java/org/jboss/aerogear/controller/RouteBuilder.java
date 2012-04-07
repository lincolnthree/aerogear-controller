package org.jboss.aerogear.controller;

public interface RouteBuilder {

    OnMethods from(String path);

    public static interface OnMethods {
        TargetEndpoint on(RequestMethod... methods);
    }

    public static interface TargetEndpoint {
        <T> T to(Class<T> clazz);
    }
}
