package org.jboss.aerogear.controller.router;

import org.jboss.aerogear.controller.RouteBuilder;
import org.jboss.aerogear.controller.RouteBuilderImpl;

import java.util.List;

public class Routes {

    private final List<RouteBuilder> routes;

    public Routes(List<RouteBuilder> routes) {
        this.routes = routes;
    }

    public static RouteBuilder route() {
        return new RouteBuilderImpl();
    }

    public static Routes from(List<RouteBuilder> routes) {
        return new Routes(routes);
    }

    @Override
    public String toString() {
        return "Routes{" +
                "routes=" + routes +
                '}';
    }
}
