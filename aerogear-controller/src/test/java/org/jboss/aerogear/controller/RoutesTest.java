package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.router.Routes;
import org.junit.Test;

import static org.jboss.aerogear.controller.RequestMethod.GET;
import static org.jboss.aerogear.controller.RequestMethod.POST;

public class RoutesTest {

    @Test
    public void basicRoute() {
        Routes routes = new AbstractRoutingModule() {
            @Override
            public void configuration() {
                route()
                        .from("/home")
                        .on(GET)
                        .to(SampleController.class).index();
                route()
                        .from("/client/:name")
                        .on(GET)
                        .to(SampleController.class).client(":name");
                route()
                        .from("/lol")
                        .on(GET, POST)
                        .to(SampleController.class).lol();
                route()
                        .from("/car/{car.id}")
                        .on(GET)
                        .to(SampleController.class).save(param("{car.id}"));
            }
        }.build();

    }

    private static <T> T param(final String s) {
        return (T) null;
    }

    public static class SampleController {

        public void index() {
        }

        public void client(String name) {
        }

        public void lol() {
        }

        public void save(Car car) {

        }
    }

    public static class Car {

        private final String name;

        public Car(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
