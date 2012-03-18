package org.jboss.aerogear.core.integration.fixtures;

import org.jboss.aerogear.core.View;

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

}
