package org.jboss.aerogear.core;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

@RunWith(Arquillian.class)
public class AeroGearTest {

    @Deployment(testable = false)
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "aerogear-test.war")
                .addClass(AeroGear.class)
                .addClass(Dummy.class)
                .addClass(Dispatcher.class);
    }

    @ArquillianResource()
    private URL url;

    @Test
    public void testIt() throws IOException, URISyntaxException {
        URL target = new URL(url + "/dummy/moar").toURI().normalize().toURL();
        URLConnection connection = target.openConnection();
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        System.out.println(bufferedReader.readLine());
        bufferedReader.close();
    }

    @Path("/dummy")
    public static class Dummy {

        @GET @Path("/moar")
        public void index() {

        }

    }

    public static class MyApplication extends Application {
        @Override
        public Set<Class<?>> getClasses() {
            return new HashSet<Class<?>>(new HashSet<Class<?>>() {{
                add(AeroGear.class);
            }});
        }
    }

}
