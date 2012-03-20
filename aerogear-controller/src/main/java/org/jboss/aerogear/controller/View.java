package org.jboss.aerogear.controller;

public class View {
    private final String viewPath;
    private final Object model;
    private final TypeNameExtractor nameExtractor = new TypeNameExtractor();

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
        if (hasModelData()) {
            return nameExtractor.nameFor(this.model.getClass());
        }
        return null;
    }

    public Object getModel() {
        return model;
    }

    public boolean hasModelData() {
        return this.model != null;
    }
}
