package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.servlet.HttpRequestResponseFactory;
import org.jboss.aerogear.controller.servlet.resteasy.ResteasyHttpRequestResponseFactory;
import org.jboss.aerogear.controller.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("text/html")
public class AeroGear implements MessageBodyWriter<Object> {

    public static final int UNKNOWN_SIZE = -1;
    private final HttpRequestResponseFactory httpRequestResponseFactory;

    public AeroGear() {
        httpRequestResponseFactory = new ResteasyHttpRequestResponseFactory();
    }

    public AeroGear(HttpRequestResponseFactory httpRequestResponseFactory) {
        this.httpRequestResponseFactory = httpRequestResponseFactory;
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return View.class.isAssignableFrom(aClass);
    }

    @Override
    public long getSize(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return UNKNOWN_SIZE;
    }

    @Override
    public void writeTo(Object o, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        View view = (View) o;

        HttpServletRequest request = httpRequestResponseFactory.getRequest();
        HttpServletResponse response = httpRequestResponseFactory.getResponse();
        try {
            if (view.hasModelData()) {
                request.setAttribute(view.getModelName(), view.getModel());
            }
            request.getRequestDispatcher(view.getViewPath()).forward(request, response);
        } catch (ServletException e) {
            throw new WebApplicationException(e);
        }
    }

}
