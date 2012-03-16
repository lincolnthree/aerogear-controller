package org.jboss.aerogear.core;

import org.jboss.aerogear.core.annotations.Controller;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static org.fest.assertions.Assertions.assertThat;

public class ControllersTest {

    @Test
    public void buildsRouteFromControllers() {
        Router router = Controllers.controllers(ControllerA.class).bootstrapRouter();
        assertThat(router.hasPath("/test1", HttpMethod.GET)).isTrue();
    }

    @Controller
    public static class ControllerA {

        @GET
        @Path("/test1")
        public void simpleMethod() {

        }

    }
}
