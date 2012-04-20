package org.jboss.aerogear.controller;

import java.util.LinkedList;
import java.util.List;

import org.jboss.aerogear.controller.routes.Route;
import org.jboss.aerogear.controller.routes.RouteBuilder;
import org.jboss.aerogear.controller.routes.RouteBuilder;
import org.jboss.aerogear.controller.routes.RoutingModule;

public abstract class AbstractRoutingModule implements RoutingModule {
    private final List<RouteBuilder> routes = new LinkedList<RouteBuilder>();

    public abstract void configuration();

    public RouteBuilder route() {
        RouteBuilder route = new RouteBuilder();
        routes.add(route);
        return route;
    }

    @Override
    public List<? extends Route> getRoutes() {
        configuration();
        return routes;
    }

    public static <T> T param(Class<T> clazz) {
        return null;
    }

    public static <T> T param() {
        return null;
    }
}
