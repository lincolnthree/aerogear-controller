package org.jboss.aerogear.core.integration;

import org.jboss.aerogear.core.AeroGear;
import org.jboss.aerogear.core.View;
import org.jboss.aerogear.core.integration.MyApplication;
import org.jboss.aerogear.core.integration.fixtures.Dummy;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

@RunWith(Arquillian.class)
public class AeroGearIT {

    @Deployment(testable = false)
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "aerogear-test.war")
                .addClass(AeroGear.class)
                .addClass(Dummy.class)
                .addClass(MyApplication.class)
                .addClass(View.class)
                .addAsWebResource("page.html", "page.html")
                .addAsWebInfResource("web.xml", "web.xml");
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
        bufferedReader.close();
    }

}
