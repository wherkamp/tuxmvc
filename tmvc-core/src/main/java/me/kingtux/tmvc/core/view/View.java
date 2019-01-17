package me.kingtux.tmvc.core.view;

public interface View {
    public View setTemplate(String mytemplate);

    public void render();

    public View set(String hey, Object s);
}
