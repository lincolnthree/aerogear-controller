package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.AbstractRoutingModule;
import org.jboss.aerogear.controller.RequestMethod;

public class Routes extends AbstractRoutingModule {

    @Override
    public void configuration() {
        route()
                .from("/")
                .on(RequestMethod.GET)
                .to(Home.class).index();
        route()
                .from("/delorean")
                .on(RequestMethod.GET)
                .to(Home.class).anotherPage();
    }
}
