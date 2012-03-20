package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.view.View;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ViewTest {

    @Test
    public void namesSimpleObjects() {
        assertThat(new View("/path/to/page.jsp", new Car()).getModelName()).isEqualTo("car");
        assertThat(new View("/path/to/page.jsp", new Car[0]).getModelName()).isEqualTo("carList");
    }

    public static class Car {

    }
}
