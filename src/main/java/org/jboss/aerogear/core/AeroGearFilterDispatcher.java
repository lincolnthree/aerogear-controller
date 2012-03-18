package org.jboss.aerogear.core;

import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;
import org.jboss.resteasy.spi.HttpResponse;

import javax.servlet.http.HttpServletResponse;

public class AeroGearFilterDispatcher extends FilterDispatcher {

    @Override
    public HttpResponse createResteasyHttpResponse(HttpServletResponse response) {
        return new DeferringHttpServletResponseWrapper(response, getDispatcher().getProviderFactory());
    }

}
