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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AeroGearTest {

    private AeroGear aeroGear;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private Router router;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        aeroGear = new AeroGear();
        aeroGear.setRouter(router);
    }

    @Test(expected = ServletException.class)
    public void onlyRunsOnServletEnvironments() throws IOException, ServletException {
        aeroGear.doFilter(mock(ServletRequest.class), mock(ServletResponse.class), mock(FilterChain.class));
    }

    @Test
    public void servesASimpleRequest() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/test1");
        when(router.hasPath(eq("/test1"))).thenReturn(true);
        when(router.hasPath(eq("/test2"))).thenReturn(false);
        aeroGear.doFilter(request, response, chain);
        verifyZeroInteractions(chain);
        verify(router).dispatch(request, response, chain);
        when(request.getRequestURI()).thenReturn("/test2");
        aeroGear.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

}
