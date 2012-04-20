package org.jboss.aerogear.controller.rewrite;

import java.util.LinkedList;
import java.util.Map;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.http.HttpServletRequest;

import org.jboss.aerogear.controller.RequestMethod;
import org.jboss.aerogear.controller.ViewResolver;
import org.jboss.aerogear.controller.routes.Route;
import org.jboss.aerogear.controller.util.StringUtils;
import org.jboss.aerogear.controller.view.View;
import org.ocpsoft.logging.Logger;
import org.ocpsoft.rewrite.config.Rule;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.http.event.HttpInboundServletRewrite;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

import br.com.caelum.iogi.Iogi;
import br.com.caelum.iogi.parameters.Parameter;
import br.com.caelum.iogi.reflection.Target;
import br.com.caelum.iogi.util.DefaultLocaleProvider;
import br.com.caelum.iogi.util.NullDependencyProvider;

public class RewriteRoutingRule implements Rule {

    private static final Logger log = Logger.getLogger(RewriteRoutingRule.class);

    private final Route route;
    private final BeanManager beanManager;
    private final ViewResolver viewResolver;

    private RewriteRoutingRule(BeanManager beanManager, ViewResolver viewResolver, Route route) {
        this.beanManager = beanManager;
        this.viewResolver = viewResolver;
        this.route = route;
    }

    public static Rule from(BeanManager beanManager, ViewResolver viewResolver, Route route) {
        return new RewriteRoutingRule(beanManager, viewResolver, route);
    }

    @Override
    public String getId() {
        return "Route_" + route.hashCode();
    }

    @Override
    public boolean evaluate(Rewrite event, EvaluationContext context) {

        return event instanceof HttpInboundServletRewrite
                && route.getMethods().contains(RequestMethod.valueOf(((HttpServletRewrite) event).getRequest().getMethod()))
                && Path.matches(route.getPath()).evaluate(event, context);
    }

    @Override
    public void perform(Rewrite event, EvaluationContext context) {
        if (event instanceof HttpInboundServletRewrite) {
            HttpServletRequest request = ((HttpServletRewrite) event).getRequest();
            try {
                Object[] params = extractParameters(request, route);
                Object result = route.getTargetMethod().invoke(getController(route), params);
                String viewPath = viewResolver.resolveViewPathFor(route);
                View view = new View(viewPath, result);

                if (view.hasModelData()) {
                    request.setAttribute(view.getModelName(), view.getModel());
                }

                // TODO this behavior should be configurable
                log.info("Forwarding to [{}]", view.getViewPath());
                Forward.to(view.getViewPath()).perform(event, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final Iogi iogi = new Iogi(new NullDependencyProvider(), new DefaultLocaleProvider());

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
