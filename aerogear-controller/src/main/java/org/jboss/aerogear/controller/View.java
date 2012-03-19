package org.jboss.aerogear.controller;

public class View {
    private final String viewPath;
    private final Object model;

    public View(String viewPath) {
        this(viewPath, null);
    }

    public View(String viewPath, Object model) {
        this.viewPath = viewPath;
        this.model = model;
    }

    public String getViewPath() {
        return viewPath;
    }

    public String getModelName() {
    }
}
