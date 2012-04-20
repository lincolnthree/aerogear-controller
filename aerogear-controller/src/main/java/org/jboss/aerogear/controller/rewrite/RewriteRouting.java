package org.jboss.aerogear.controller.rewrite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.aerogear.controller.RequestMethod;
import org.jboss.aerogear.controller.ViewResolver;
import org.jboss.aerogear.controller.routes.Route;
import org.jboss.aerogear.controller.routes.RoutingModule;
import org.jboss.aerogear.controller.util.StringUtils;
import org.jboss.aerogear.controller.view.View;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpCondition;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.HttpOperation;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

import br.com.caelum.iogi.Iogi;
import br.com.caelum.iogi.parameters.Parameter;
import br.com.caelum.iogi.reflection.Target;
import br.com.caelum.iogi.util.DefaultLocaleProvider;
import br.com.caelum.iogi.util.NullDependencyProvider;

public class RewriteRouting extends HttpConfigurationProvider {

    private final BeanManager beanManager;
    private final ViewResolver viewResolver;
    private final Iogi iogi = new Iogi(new NullDependencyProvider(), new DefaultLocaleProvider());

    private final List<Route> routes = new ArrayList<Route>();

    @Inject
    public RewriteRouting(Instance<RoutingModule> routingModules, BeanManager beanManager, ViewResolver viewResolver) {
        this.beanManager = beanManager;
        this.viewResolver = viewResolver;

        for (RoutingModule module : routingModules) {
            routes.addAll(module.getRoutes());
        }
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public Configuration getConfiguration(ServletContext context) {
        HttpCondition routeExists = new HttpCondition() {
            @Override
            public boolean evaluateHttp(HttpServletRewrite event, EvaluationContext context) {
                return hasRouteFor(event.getRequest());
            }
        };

        HttpOperation route = new HttpOperation() {
            @Override
            public void performHttp(HttpServletRewrite event, EvaluationContext context) {
                HttpServletRequest request = event.getRequest();
                try {
                    Route route = routeFor(extractMethod(request), extractPath(request));
                    Object[] params = extractParameters(request, route);
                    Object result = route.getTargetMethod().invoke(getController(route), params);
                    String viewPath = viewResolver.resolveViewPathFor(route);
                    View view = new View(viewPath, result);
                    if (view.hasModelData()) {
                        request.setAttribute(view.getModelName(), view.getModel());
                    }

                    Forward.to(view.getViewPath()).perform(event, context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        return ConfigurationBuilder.begin().defineRule().when(Direction.isInbound().and(routeExists)).perform(route);

    }

    public boolean hasRouteFor(HttpServletRequest httpServletRequest) {
        return hasRouteFor(extractMethod(httpServletRequest), extractPath(httpServletRequest));
    }

    public boolean hasRouteFor(RequestMethod method, String requestURI) {
        System.out.println(method + requestURI);
        for (Route route : routes) {
            if (route.getMethods().contains(method) && route.getPath().equals(requestURI)) {
                return true;
            }
        }
        return false;
    }

    public Route routeFor(RequestMethod method, String requestURI) {
        for (Route route : routes) {
            if (route.getMethods().contains(method) && route.getPath().equals(requestURI)) {
                return route;
            }
        }
        throw new RuntimeException("route not found");
    }

    private String extractPath(HttpServletRequest httpServletRequest) {
        ServletContext servletContext = httpServletRequest.getServletContext();
        String contextPath = servletContext.getContextPath();

        return httpServletRequest.getRequestURI().substring(contextPath.length());
    }

    private RequestMethod extractMethod(HttpServletRequest httpServletRequest) {
        return RequestMethod.valueOf(httpServletRequest.getMethod());
    }

    private Object[] extractParameters(HttpServletRequest request, Route route) {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] value = entry.getValue();
            if (value.length == 1) {
                parameters.add(new Parameter(entry.getKey(), value[0]));
            } else {
                System.out.println("oops, multivalued params not supported yet");
                continue;
            }
        }
        Class<?>[] parameterTypes = route.getTargetMethod().getParameterTypes();
        if (parameterTypes.length == 1) {
            Class<?> parameterType = parameterTypes[0];
            Target<?> target = Target.create(parameterType, StringUtils.downCaseFirst(parameterType.getSimpleName()));
            Object instantiate = iogi.instantiate(target, parameters.toArray(new Parameter[] {}));
            return new Object[] { instantiate };
        }

        return new Object[0];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Object getController(Route route) {
        Bean next = beanManager.getBeans(route.getTargetClass()).iterator().next();
        return next.create(beanManager.createCreationalContext(next));
    }

}
