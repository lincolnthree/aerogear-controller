package org.jboss.aerogear.haml;

import org.jruby.CompatVersion;
import org.jruby.embed.ScriptingContainer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@WebFilter(filterName = "aerogear-haml", urlPatterns = "*.haml")
public class HamlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!isHttpServletContainer(request, response)) {
            throw new ServletException("must be run inside a Servlet container");
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        ScriptingContainer container = new ScriptingContainer();
        container.setCompatVersion(CompatVersion.RUBY1_9);
//        container.put("data", extractLocals(httpServletRequest));
        container.put("page", extractPageLocation(httpServletRequest));
        container.put("writer", httpServletResponse.getWriter());
        String script = "require 'rubygems'\n" +
                "require 'haml'\n" +
                "puts page\n" +
                "result = Haml::Engine.new(open(page).read).render(Object.new, {})\n" +
                "writer.println(result)\n";
        container.runScriptlet(script);
    }

    private String extractPageLocation(HttpServletRequest httpServletRequest) throws FileNotFoundException, ServletException {
        ServletContext servletContext = httpServletRequest.getServletContext();
        String contextPath = Pattern.quote(servletContext.getContextPath());
        String requestURI = httpServletRequest.getRequestURI().replaceFirst(contextPath, "");
        return servletContext.getRealPath(requestURI);
    }

    private Map<String, Object> extractLocals(HttpServletRequest httpServletRequest) {
        Map<String, Object> locals = new HashMap<String, Object>();
        Enumeration<String> attributeNames = httpServletRequest.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            locals.put(attributeName, httpServletRequest.getAttribute(attributeName));
        }
        return Collections.unmodifiableMap(locals);
    }

    private boolean isHttpServletContainer(ServletRequest request, ServletResponse response) {
        return request instanceof HttpServletRequest && response instanceof HttpServletResponse;
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
