package me.kingtux.tuxmvc.core.emails;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.view.View;

public interface EmailManager {


    Website getWebsite();

    void sendEmail(String to, String title, String content);

    default void sendEmail(String to, String title, View content) {
        sendEmail(to, title, getWebsite().getViewManager().parseView(content));
    }

    boolean isEmail(String s);
}
