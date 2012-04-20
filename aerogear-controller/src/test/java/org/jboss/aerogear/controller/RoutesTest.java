package org.jboss.aerogear.controller;

import static org.fest.assertions.Assertions.assertThat;
import static org.jboss.aerogear.controller.RequestMethod.GET;
import static org.jboss.aerogear.controller.RequestMethod.POST;

import java.util.List;

import org.jboss.aerogear.controller.routes.Route;
import org.junit.Test;

public class RoutesTest {

    @Test
    public void basicRoute() {
        List<? extends Route> routes = new AbstractRoutingModule() {
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
        }.getRoutes();

    }

    @Test
    public void routesWithParameters() throws SecurityException, NoSuchMethodException {
        List<? extends Route> routes = new AbstractRoutingModule() {
            @Override
            public void configuration() {
                route()
                        .from("/cars")
                        .on(POST)
                        .to(SampleController.class).save(param(Car.class));
            }
        }.getRoutes();

        assertThat(routes.get(0).getMethods().contains(POST));
        assertThat(routes.get(0).getPath()).isEqualTo("/cars");
        assertThat(routes.get(0).getTargetClass()).isEqualTo(SampleController.class);
        assertThat(routes.get(0).getTargetMethod()).isEqualTo(SampleController.class.getMethod("save", Car.class));
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
