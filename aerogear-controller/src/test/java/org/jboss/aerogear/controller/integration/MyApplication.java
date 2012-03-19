package org.jboss.aerogear.controller.integration;

import org.jboss.aerogear.controller.AeroGear;
import org.jboss.aerogear.controller.integration.fixtures.Dummy;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(new HashSet<Class<?>>() {{
            add(AeroGear.class);
            add(Dummy.class);
        }});
    }
}
