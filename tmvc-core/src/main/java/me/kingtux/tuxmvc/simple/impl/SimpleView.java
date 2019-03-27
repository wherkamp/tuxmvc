package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxmvc.core.view.View;
import org.jtwig.JtwigModel;

public class SimpleView implements View {
    private String template="";
    private JtwigModel jTwigModel = new JtwigModel();

    public JtwigModel getjTwigModel() {
        return jTwigModel;
    }

    @Override
    public View setTemplate(String s) {
        template = s;
        return this;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public View set(String s, Object o) {
        jTwigModel.with(s, o);
        return this;
    }
}
