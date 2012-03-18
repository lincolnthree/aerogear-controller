package org.jboss.aerogear.core.integration;

import org.jboss.aerogear.core.AeroGear;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(new HashSet<Class<?>>() {{
            add(AeroGear.class);
            add(AeroGearIT.Dummy.class);
        }});
    }
}
