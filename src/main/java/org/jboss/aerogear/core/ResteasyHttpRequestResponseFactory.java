package org.jboss.aerogear.core;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
public class ResteasyHttpRequestResponseFactory implements HttpRequestResponseFactory {

    @Override
    public HttpServletResponse getResponse() {
        return ResteasyProviderFactory.getContextData(HttpServletResponse.class);
    }

    @Override
    public HttpServletRequest getRequest() {
        return ResteasyProviderFactory.getContextData(HttpServletRequest.class);
    }
}