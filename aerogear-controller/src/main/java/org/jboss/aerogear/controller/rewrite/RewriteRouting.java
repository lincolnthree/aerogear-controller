package org.jboss.aerogear.controller.rewrite;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.jboss.aerogear.controller.ViewResolver;
import org.jboss.aerogear.controller.routes.Route;
import org.jboss.aerogear.controller.routes.RoutingModule;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;

public class RewriteRouting extends HttpConfigurationProvider {

    private final BeanManager beanManager;
    private final ViewResolver viewResolver;

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

        ConfigurationBuilder result = ConfigurationBuilder.begin();

        for (Route route : routes) {
            result.addRule(RewriteRoutingRule.from(beanManager, viewResolver, route));
        }

        return result;

    }

}
