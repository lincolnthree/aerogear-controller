package org.jboss.aerogear.controller;

public class RouteBuilderImpl implements RouteBuilder {
    @Override
    public OnMethods from(String path) {
        return new OnMethodsImpl(path);
    }
}
