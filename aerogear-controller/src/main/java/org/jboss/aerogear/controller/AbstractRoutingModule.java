package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.router.Routes;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRoutingModule implements RoutingModule {
    private List<RouteBuilder> routes = new LinkedList<RouteBuilder>();

    public abstract void configuration();

    public RouteBuilder route() {
        RouteBuilder route = Routes.route();
        routes.add(route);
        return route;
    }

    @Override
    public Routes build() {
        configuration();
        return Routes.from(routes);
    }
}
