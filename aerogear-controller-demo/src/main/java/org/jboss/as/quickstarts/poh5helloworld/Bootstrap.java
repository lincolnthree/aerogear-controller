package org.jboss.as.quickstarts.poh5helloworld;

import org.jboss.aerogear.controller.AeroGear;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class Bootstrap extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>() {{
            add(AeroGear.class);
            add(HelloWorld.class);
        }};
    }
}
