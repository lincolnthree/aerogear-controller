package org.jboss.aerogear.core;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public class AeroGearTest {

    private AeroGear aeroGear;

    @Before
    public void setUp() throws Exception {
        aeroGear = new AeroGear();
    }

    @Test(expected = ServletException.class)
    public void onlyRunsOnServletEnvironments() throws IOException, ServletException {
        aeroGear.doFilter(mock(ServletRequest.class), mock(ServletResponse.class), mock(FilterChain.class));
    }
}
