package org.jboss.aerogear.core;

import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "AeroGear", urlPatterns = "/*")
public class Dispatcher extends FilterDispatcher {
}
