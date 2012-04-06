package org.jboss.aerogear.controller;

public class RouteBuilderImpl implements RouteBuilder {
    @Override
    public RouteDescriptor from(String path) {
        return new RouteDescriptorImpl(path);
    }
}
