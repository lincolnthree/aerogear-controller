package org.jboss.as.quickstarts.poh5helloworld;

import org.jboss.aerogear.controller.AbstractRoutingModule;
import org.jboss.aerogear.controller.RequestMethod;

public class Routes extends AbstractRoutingModule {

    @Override
    public void configuration() {
        route()
                .from("/home")
                .on(RequestMethod.GET)
                .to(Home.class).index();
    }
}
