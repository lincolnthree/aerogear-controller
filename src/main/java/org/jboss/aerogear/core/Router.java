package org.jboss.aerogear.core;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Router {

    boolean hasPath(String eq);

    void dispatch(HttpServletRequest request, HttpServletResponse response, FilterChain chain);
}
