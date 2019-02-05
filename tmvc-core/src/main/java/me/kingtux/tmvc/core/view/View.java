package me.kingtux.tmvc.core.view;

/**
 * A view contains all the needed variables and stuff for rendering a template
 *
 * @author KingTux
 */
public interface View {
    /**
     * Set the template file
     *
     * @param mytemplate the template file name. No Need for the extension
     * @return the View
     */
    View setTemplate(String mytemplate);

    /**
     * Gets the template file name
     *
     * @return the templates file name
     */
    String getTemplate();

    /**
     * Sets a variable
     *
     * @param hey the key
     * @param s   the value
     * @return the view
     */
    View set(String hey, Object s);
}
