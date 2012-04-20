package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.routes.Route;

public interface ViewResolver {
    String resolveViewPathFor(Route route);
}
