package me.kingtux.tuxmvc.core.emails;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.view.View;

import java.util.function.Consumer;

public interface EmailManager {


    Website getWebsite();

    void sendEmail(String to, String title, String content);

    default void sendEmail(String to, String title, View content) {
        sendEmail(to, title, getWebsite().getViewManager().parseView(content));
    }

    default void sendEmail(String to, String title, Consumer<View> content) {
        View view = getWebsite().getViewManager().buildView(getWebsite());
        content.accept(view);
        sendEmail(to, title, view);
    }

    boolean isEmail(String s);
}
