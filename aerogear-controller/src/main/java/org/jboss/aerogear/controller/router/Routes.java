package org.jboss.aerogear.controller.router;

import org.jboss.aerogear.controller.RouteBuilder;
import org.jboss.aerogear.controller.RouteBuilderImpl;

public class Routes {

    public static RouteBuilder route() {
        return new RouteBuilderImpl();
    }
}
