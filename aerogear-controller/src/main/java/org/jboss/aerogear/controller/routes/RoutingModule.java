package org.jboss.aerogear.controller.routes;

import java.util.List;

public interface RoutingModule {
    List<? extends Route> getRoutes();
}
