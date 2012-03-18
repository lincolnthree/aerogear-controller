package org.jboss.aerogear.core.integration;

import org.jboss.aerogear.core.integration.fixtures.Dummy;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

@RunWith(Arquillian.class)
public class AeroGearIT {

    @Deployment(testable = false)
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "aerogear-test.war")
                .addPackage("org.jboss.aerogear.core")
                .addClasses(Dummy.class, MyApplication.class)
                .addAsWebResource("page.jsp", "page.jsp")
                .addAsWebResource("page.html", "page.html")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web.xml", "web.xml");
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testStaticHtmlDispatch() throws IOException, URISyntaxException {
        URL target = getNormalizedURL("/dummy/moar");
        URLConnection connection = target.openConnection();
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.close();
    }

    @Test
    public void testDynamicJspDispatch() throws IOException, URISyntaxException {
        URL target = getNormalizedURL("/dummy/moarJsp");
        URLConnection connection = target.openConnection();
        connection.connect();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.close();
    }

    private URL getNormalizedURL(String targetPath) throws MalformedURLException, URISyntaxException {
        return new URL(url + targetPath).toURI().normalize().toURL();
    }
}
