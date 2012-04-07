package org.jboss.aerogear.controller;

import org.jboss.aerogear.controller.router.Routes;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Named
public class DefaultRouter implements Router {

    private Routes routes;
    private final BeanManager beanManager;

    @Inject
    public DefaultRouter(RoutingModule routes, BeanManager beanManager) {
        this.routes = routes.build();
        this.beanManager = beanManager;
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
    public void dispatch(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            Route route = routes.routeFor(extractMethod(request), extractPath(request));
            Object result = route.getTargetMethod().invoke(getController(route));
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Object getController(Route route) {
        Bean next = beanManager.getBeans(route.getTargetClass()).iterator().next();
        return next.create(beanManager.createCreationalContext(next));
    }
}
