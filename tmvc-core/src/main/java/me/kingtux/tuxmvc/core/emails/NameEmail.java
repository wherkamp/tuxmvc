package me.kingtux.tuxmvc.core.emails;

public class NameEmail {

    private String name, email;

    public NameEmail(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
