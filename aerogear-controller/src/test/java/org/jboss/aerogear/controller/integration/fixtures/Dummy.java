package org.jboss.aerogear.controller.integration.fixtures;

import org.jboss.aerogear.controller.view.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/dummy")
public class Dummy {

    @GET
    @Path("/moar")
    @Produces("text/html")
    public View index() {
        return new View("/page.html");
    }

    @GET
    @Path("/moarJsp")
    @Produces("text/html")
    public View moarJsp() {
        return new View("/page.jsp");
    }

}
