package services;

import Exceptions.SenderException;
import persistence.entities.Mail;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender {
    private static String username = "georgybocharovfrtc@gmail.com";
    private static String password = "Georg_The_Hunter1998";


    public static boolean send(Mail mail) throws SenderException {
        Properties props;
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getEmail()));
            message.setSubject(mail.getSubject());
            message.setText(mail.getMessage());
            Transport.send(message);

            System.out.println("i sent email to " + mail.getEmail());

        } catch (MessagingException e) {
            throw new SenderException(mail.getEmail(), e);
        }
        return true;
    }
}
