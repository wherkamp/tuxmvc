package me.kingtux.tuxmvc.core.emails;

public interface EmailBuilder {
    EmailBuilder subject(String s);

    EmailBuilder content(String s);

    EmailBuilder to(String s, String email);

    EmailBuilder cc(String s, String email);

    EmailBuilder from(String s, String email);
}
