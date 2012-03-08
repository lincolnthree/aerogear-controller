package org.jboss.aerogear.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AeroGearTest {

    private AeroGear aeroGear;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        aeroGear = new AeroGear();
    }

    @Test(expected = ServletException.class)
    public void onlyRunsOnServletEnvironments() throws IOException, ServletException {
        aeroGear.doFilter(mock(ServletRequest.class), mock(ServletResponse.class), mock(FilterChain.class));
    }

    @Test
    public void servesASimpleRequest() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/test1");
        aeroGear.doFilter(request, response, chain);
    }
}
