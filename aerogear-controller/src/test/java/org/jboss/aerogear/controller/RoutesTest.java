package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.router.Routes;
import org.junit.Test;

public class RoutesTest {

    @Test
    public void basicRoute() {
        Routes routes = new AbstractRoutingModule() {
            @Override
            void configuration() {
                route()
                        .from("/home")
                        .on("GET")
                        .to(SampleController.class).index();
                route()
                        .from("/client/:name")
                        .on("GET")
                        .to(SampleController.class).client(":name");
            }
        }.build();

    }

    public static class SampleController {

        public void index() {

        }

        public void client(String name) {
        }
    }
}
