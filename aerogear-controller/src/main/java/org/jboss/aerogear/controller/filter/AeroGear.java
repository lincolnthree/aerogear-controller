package org.jboss.aerogear.controller.filter;

import org.jboss.aerogear.controller.AbstractRoutingModule;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "aerogear-controller", urlPatterns = "/*")
public class AeroGear implements Filter {

    @Inject
    private AbstractRoutingModule routingModule;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(routingModule.build());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
