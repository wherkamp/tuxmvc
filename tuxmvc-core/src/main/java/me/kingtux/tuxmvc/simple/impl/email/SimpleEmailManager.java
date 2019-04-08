package me.kingtux.tuxmvc.simple.impl.email;

import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.core.emails.EmailManager;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class SimpleEmailManager implements EmailManager {
    Website site;
    Mailer mailer;
    String fromName, from;


    public static SEmailBuilder buildEmailManager(String host, String port, TransportStrategy strategy, String from, String fromPassword, String fromName) {
        return new SEmailBuilder(host, port, strategy, from, fromPassword, fromName);
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
        mailer.sendMail(email, true);
    }

    public static class SEmailBuilder {
        private String host, from, fromPassword, fromName;
        private int port;
        private TransportStrategy strategy;
        private Website site;

        public SEmailBuilder(String host, String port, TransportStrategy strategy, String from, String fromPassword, String fromName) {
            this.host = host;
            this.port = Integer.parseInt(port);
            this.strategy = strategy;
            this.from = from;
            this.fromPassword = fromPassword;
            this.fromName = fromName;
        }

        public SEmailBuilder setSite(Website site) {
            this.site = site;
            return this;
        }

        public EmailManager build() {
            SimpleEmailManager manager = new SimpleEmailManager();
            manager.mailer = MailerBuilder.withSMTPServer(host, port, from, fromPassword).withTransportStrategy(strategy).buildMailer();

            manager.from = from;
            manager.fromName = fromName;
            manager.site = site;
            return manager;
        }
    }
}