package org.jboss.aerogear.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestResponseFactory {
    HttpServletResponse getResponse();

    HttpServletRequest getRequest();
}
