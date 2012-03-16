package org.jboss.aerogear.core;

import org.jboss.aerogear.core.annotations.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

public class Controllers {

    private Class<?>[] controllers;

    private Controllers(Class<?>[] controllers) {
        this.controllers = controllers;
    }

    public static Controllers controllers(Class<?>... controllers) {
        for (Class<?> controller : controllers) {
            if (!controller.isAnnotationPresent(Controller.class)) {
                throw new RuntimeException("invalid controller, controllers must be annotated with @Controller");
            }
        }
        return new Controllers(controllers);
    }

    public Router bootstrapRouter() {
        LinkedList<PathInfo> list = new LinkedList<PathInfo>();
        for (Class<?> controller : controllers) {
            Method[] methods = controller.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Path.class)) {
                    String path = method.getAnnotation(Path.class).value();
                    HttpMethod httpMethod = extractHttpMethod(method);
                }
            }
        }
        return null;
    }

    private Collection<HttpMethod> extractHttpMethod(Method method) {
        Class<? extends Annotation>[] annotations = new Class<? extends Annotation>[]{GET.class, POST.class, PUT.class};
        for (Class<? extends Annotation> annotation : annotations) {
            if (method.isAnnotationPresent(annotation)) {

            }
        }

    }
}
