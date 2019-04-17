package me.kingtux.tuxmvc.simple.impl.email;

import me.kingtux.tuxmvc.TuxMVC;
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


    public static SEmailBuilder buildEmailManager(String host, String port, String strategy, String from, String fromPassword, String fromName) {
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
        if (mailer == null) {
            TuxMVC.TUXMVC_LOGGER.warn("No Email Configuration found skipping!");
            return;
        }
        Email email = EmailBuilder.startingBlank().to(to).withSubject(title).withHTMLText(content).from(fromName, from).withReplyTo(from).buildEmail();
        mailer.sendMail(email, true);
    }

    public static class SEmailBuilder {
        private String host, from, fromPassword, fromName;
        private int port;
        private TransportStrategy strategy;
        private Website site;

        public SEmailBuilder(String host, String port, String strategy, String from, String fromPassword, String fromName) {
            if (host.isEmpty() && from.isEmpty() && port.isEmpty()) {
                TuxMVC.TUXMVC_LOGGER.warn("No Email Configuration found skipping!");
                return;
            }
            this.host = host;
            this.port = Integer.parseInt(port);
            this.strategy =TransportStrategy.valueOf(strategy);
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

            if (host == null && from == null && port == 0) {
                return manager;
            }
            try {
                manager.mailer = MailerBuilder.withSMTPServer(host, port, from, fromPassword).withTransportStrategy(strategy).buildMailer();
            } catch (Exception e) {
                TuxMVC.TUXMVC_LOGGER.error("Unable to Configure Email Server", e);
            }
            manager.from = from;
            manager.fromName = fromName;
            manager.site = site;
            return manager;
        }
    }
}