package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.router.Routes;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
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

            }
        }.build();

    }

    @Test
    public void routesWithParameters() {
        Routes routes = new AbstractRoutingModule() {
            @Override
            public void configuration() {
                route()
                        .from("/cars")
                        .on(POST)
                        .to(SampleController.class).save(param(Car.class));
            }
        }.build();
        assertThat(routes.hasRouteFor(POST, "/cars")).isTrue();
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

        @Override
        public String toString() {
            return "Car{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
