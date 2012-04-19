package org.jboss.aerogear.controller.demo;

import org.jboss.aerogear.controller.AbstractRoutingModule;
import org.jboss.aerogear.controller.RequestMethod;
import org.jboss.aerogear.controller.demo.model.Car;

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
        route()
                .from("/cars")
                .on(RequestMethod.POST)
                .to(Home.class).save(param(Car.class));
    }
}
