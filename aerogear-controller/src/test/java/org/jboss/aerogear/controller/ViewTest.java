package org.jboss.aerogear.controller;

import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ViewTest {

    @Test
    @Ignore
    public void namesSimpleObjects() {
        View view = new View("/path/to/page.jsp", new Car());
        assertThat(view.getModelName()).isEqualTo("car");
    }

    public static class Car {

    }
}
