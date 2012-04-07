package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.router.Routes;
import org.jboss.aerogear.controller.view.View;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultRouter implements Router {

    private Routes routes;
    private final BeanManager beanManager;
    private ViewResolver viewResolver;

    @Inject
    public DefaultRouter(RoutingModule routes, BeanManager beanManager, ViewResolver viewResolver) {
        this.routes = routes.build();
        this.beanManager = beanManager;
        this.viewResolver = viewResolver;
    }

    @Override
    public boolean hasRouteFor(HttpServletRequest httpServletRequest) {
        return routes.hasRouteFor(extractMethod(httpServletRequest), extractPath(httpServletRequest));
    }

    private String extractPath(HttpServletRequest httpServletRequest) {
        ServletContext servletContext = httpServletRequest.getServletContext();
        String contextPath = servletContext.getContextPath();

        return httpServletRequest.getRequestURI().substring(contextPath.length());
    }

    private RequestMethod extractMethod(HttpServletRequest httpServletRequest) {
        return RequestMethod.valueOf(httpServletRequest.getMethod());
    }

    @Override
    public void dispatch(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException {
        try {
            Route route = routes.routeFor(extractMethod(request), extractPath(request));
            Object result = route.getTargetMethod().invoke(getController(route));
            String viewPath = viewResolver.resolveViewPathFor(route);
            View view = new View(viewPath, result);
            if (view.hasModelData()) {
                request.setAttribute(view.getModelName(), view.getModel());
            }
            request.getRequestDispatcher(view.getViewPath()).forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private Object getController(Route route) {
        Bean next = beanManager.getBeans(route.getTargetClass()).iterator().next();
        return next.create(beanManager.createCreationalContext(next));
    }
}
