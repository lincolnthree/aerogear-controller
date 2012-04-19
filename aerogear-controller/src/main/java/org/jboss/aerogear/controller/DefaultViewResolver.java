package org.jboss.aerogear.controller;

public class DefaultViewResolver implements ViewResolver {

    private static final String DEFAULT_PREFIX = "/WEB-INF/pages";
    private static final String DEFAULT_TEMPLATE_FORMAT = ".jsp";

    @Override
    public String resolveViewPathFor(Route route) {
        String folder = route.getTargetClass().getSimpleName();
        String name = route.getTargetMethod().getName();

        return String.format("%s/%s/%s.jsp", DEFAULT_PREFIX, folder, name, DEFAULT_TEMPLATE_FORMAT);
    }
}
