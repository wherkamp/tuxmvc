package me.kingtux.tuxmvc.simple.impl.email;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.emails.EmailManager;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class SimpleEmailManager implements EmailManager {
    private Website site;
    private Mailer mailer;
    private String fromName, from;


    public static EmailManager buildEmailManager(String host, String port, TransportStrategy strategy, String from, String fromPassword, String fromName) {
        SimpleEmailManager manager = new SimpleEmailManager();
        manager.mailer = MailerBuilder.withSMTPServer(host, Integer.parseInt(port), from, fromPassword).withTransportStrategy(strategy).buildMailer();
        manager.from = from;
        manager.fromName = fromName;
        return manager;
    }

    public void setSite(Website site) {
        this.site = site;
    }

    @Override
    public Website getWebsite() {
        return site;
    }

    public Mailer getMailer() {
        return mailer;
    }

    @Override
    public void sendEmail(String to, String title, String content) {
        Email email = EmailBuilder.startingBlank().to(to).withSubject(title).withHTMLText(content).from(fromName, from).withReplyTo(from).buildEmail();
        mailer.sendMail(email);
    }
}
