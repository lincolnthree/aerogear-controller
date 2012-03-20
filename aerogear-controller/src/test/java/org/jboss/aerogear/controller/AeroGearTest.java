package org.jboss.aerogear.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AeroGearTest {

    private AeroGear aeroGear;
    private Annotation[] annotations;
    private MediaType mediaType;
    @Mock
    private MultivaluedMap<String, Object> httpHeaders;
    @Mock
    private OutputStream outputStream;
    @Mock
    private HttpRequestResponseFactory httpRequestResponseFactory;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(httpRequestResponseFactory.getRequest()).thenReturn(httpServletRequest);
        when(httpRequestResponseFactory.getResponse()).thenReturn(httpServletResponse);
        aeroGear = new AeroGear(httpRequestResponseFactory);
        annotations = new Annotation[]{};
        mediaType = new MediaType("text", "html");
    }

    @Test
    public void onlyProcessViewActions() {
        assertThat(aeroGear.isWriteable(View.class, View.class, annotations, mediaType)).isTrue();
        assertThat(aeroGear.isWriteable(Object.class, View.class, annotations, mediaType)).isFalse();
    }

    @Test
    public void neverKnowsTheContentSize() {
        assertThat(aeroGear.getSize(new Object(), View.class, View.class, annotations, mediaType)).isEqualTo(AeroGear.UNKNOWN_SIZE);
    }

    @Test
    public void dispatchesRenderingToTheRouter() throws IOException, ServletException {
        View view = new View("/page.jsp");
        when(httpServletRequest.getRequestDispatcher(view.getViewPath())).thenReturn(dispatcher);
        aeroGear.writeTo(view, View.class, View.class, annotations, mediaType, httpHeaders, outputStream);
        verify(dispatcher).forward(httpServletRequest, httpServletResponse);
    }

    @Test
    public void putsModelObjectsAsRequestAttributes() throws IOException {
        Yatch model = new Yatch();
        View view = new View("/page.jsp", model);
        when(httpServletRequest.getRequestDispatcher(view.getViewPath())).thenReturn(dispatcher);
        aeroGear.writeTo(view, View.class, View.class, annotations, mediaType, httpHeaders, outputStream);
        verify(httpServletRequest).setAttribute(eq("yatch"), eq(model));
    }

    public static class Yatch {

    }
}
