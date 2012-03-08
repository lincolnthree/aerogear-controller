package org.jboss.aerogear.core;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AeroGear", urlPatterns = "*")
public class AeroGear implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isHttpServletContainer(request, response)) {
            throw new ServletException("must be run inside a Servlet container");
        }
    }

    private boolean isHttpServletContainer(ServletRequest request, ServletResponse response) {
        return request instanceof HttpServletRequest && response instanceof HttpServletResponse;
    }

    @Override
    public void destroy() {
    }
}
