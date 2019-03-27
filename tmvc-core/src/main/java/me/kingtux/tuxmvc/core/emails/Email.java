package me.kingtux.tuxmvc.core.emails;

import java.util.List;

public interface Email {

    String subject();

    String content();

    NameEmail from();

    List<NameEmail> to();

    List<NameEmail> cc();


    void send();
}
