package com.epam.utility.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Properties;

public class EmailSender {

    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);

    private static final String EMAIL_SUBJECT = "Leave Feedback";
    private static final String EMAIL_CONTENT = "<h3>Dear, %s!</h3>" +
            "<p>Please leave your feedback about our service.</p>" +
            "<p>It helps us become better.</p>";
    private static final String EMAIL_SENT = "Email sent successfully to ";

    public static boolean sendEmail(String emailTo, String name)
    {
        LOGGER.info("Sending email to " + emailTo);
        String status = null;

        Properties properties = new Properties();
        // SMTP Host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        // SSL Port
        properties.put("mail.smtp.socketFactory.port", "465");
        // SSL Factory Class
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // Enabling SMTP Authentication
        properties.put("mail.smtp.auth", "true");
        // SMTP Port
        properties.put("mail.smtp.port", "465");

        String emailFrom = "beautysalon.epam@gmail.com";
        String emailPassword = "hegupalorqvcrgcz";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override // Override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, emailPassword);
            }
        });

        try {
            // Email message
            MimeMessage message = new MimeMessage(session);
            // Setting header fields
            message.setFrom(new InternetAddress(emailFrom, "Beauty Salon", "charset=UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            // Subject line
            message.setSubject(EMAIL_SUBJECT);
            // Actual mail body
            message.setContent(String.format(EMAIL_CONTENT, name), "text/html; charset=UTF-8");
            // Send message
            Transport.send(message);
            LOGGER.info(EMAIL_SENT + emailTo);
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.error("Error during sending email to " + emailTo + Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    static void emailProcess(){
        // TODO
        // Get email list for sending: client first and last names, email, master name and service name
        boolean result = sendEmail("tytukhvolodymyr@gmail.com", "Client Name");
        LOGGER.info(result);
    }

    static void emailProcessTest(){
        LOGGER.info("emailProcessTest: " + LocalDateTime.now());
    }
}
