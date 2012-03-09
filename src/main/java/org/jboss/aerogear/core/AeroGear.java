package org.jboss.aerogear.core;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AeroGear", urlPatterns = "*")
public class AeroGear implements Filter {

    private Router router;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isHttpServletContainer(request, response)) {
            throw new ServletException("must be run inside a Servlet container");
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (router.hasPath(httpServletRequest.getRequestURI())) {
            router.dispatch(httpServletRequest, httpServletResponse, chain);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isHttpServletContainer(ServletRequest request, ServletResponse response) {
        return request instanceof HttpServletRequest && response instanceof HttpServletResponse;
    }

    @Override
    public void destroy() {
    }

    @Inject
    public void setRouter(Router router) {
        this.router = router;
    }
}
