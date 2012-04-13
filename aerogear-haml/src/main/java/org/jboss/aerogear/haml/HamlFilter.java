package org.jboss.aerogear.haml;

import org.jruby.CompatVersion;
import org.jruby.embed.ScriptingContainer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
        container.put("locals", extractLocals(httpServletRequest));
        container.put("page", extractPage(httpServletRequest));
        String script = "require 'rubygems'\n" +
                "require 'haml'\n" +
                "Haml::Engine.new(page).render(Object.new, locals)";
        Object result = container.runScriptlet(script);
        String renderedPage = container.getInstance(result, String.class);
        httpServletResponse.getWriter().print(renderedPage);
    }

    private String extractPage(HttpServletRequest httpServletRequest) throws FileNotFoundException, ServletException {
        ServletContext servletContext = httpServletRequest.getServletContext();
        InputStream resourceAsStream = servletContext.getResourceAsStream(httpServletRequest.getRequestURI());
        if (resourceAsStream != null) {
            InputStreamReader reader = new InputStreamReader(resourceAsStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String tmp = null;
            try {
                while ((tmp = bufferedReader.readLine()) != null) {
                    builder.append(tmp);
                }
                return builder.toString();
            } catch (IOException e) {
                throw new ServletException(e);
            } finally {
                try {
                    bufferedReader.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new ServletException("null resource");
        }

    }

    private Map<String, Object> extractLocals(HttpServletRequest httpServletRequest) {
        Map<String, Object> locals = new HashMap<String, Object>();
        Enumeration<String> attributeNames = httpServletRequest.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            locals.put(attributeName, httpServletRequest.getAttribute(attributeName));
        }
        return locals;
    }

    private boolean isHttpServletContainer(ServletRequest request, ServletResponse response) {
        return request instanceof HttpServletRequest && response instanceof HttpServletResponse;
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
