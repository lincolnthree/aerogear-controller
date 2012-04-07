package org.jboss.aerogear.controller;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Router {
    boolean hasRouteFor(HttpServletRequest httpServletRequest);

    void dispatch(HttpServletRequest request, HttpServletResponse response, FilterChain chain);
}
