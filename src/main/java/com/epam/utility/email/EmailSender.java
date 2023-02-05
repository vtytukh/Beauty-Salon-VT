package com.epam.utility.email;

import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.*;
import com.epam.model.Service;
import com.epam.service.*;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);

    /*private static final String EMAIL_SUBJECT = "Leave Feedback";
    private static final String EMAIL_CONTENT = "<h3>Dear, %s!</h3>" +
            "<p>Please leave your feedback about our service.</p>" +
            "<p>It helps us become better.</p>";*/

    private static final String EMAIL_SUBJECT = "Залиште відгук про BeautySalon VT";
    private static final String EMAIL_CONTENT = "<h3>Шановна(ий), %s!</h3>" +
            "<p>Будь ласка, залиште відгук для <strong>%s</strong> за надану послугу <em>%s</em>.</p>" +
            "<p>Завдяки Вашим відгукам ми покращуємо наш сервіс.</p>" +
            "<a href=\"http://localhost:8080/beauty-salon/order/comment?id=%s\">Залишити відгук</a>";
    private static final String EMAIL_SENT = "Email sent successfully to ";

    public static boolean sendEmail(String emailTo, String clientName,
                                    String masterName, String serviceName, String recordId)
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
            message.setContent(String.format(EMAIL_CONTENT, clientName,
                    masterName, serviceName, recordId), "text/html; charset=UTF-8");
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
        // Get email list for sending: client first and last names, email, master name and service name
        UserDAO userDAO = UserDAO.getInstance();
        UserService user = new UserService(userDAO);
        ServiceDAO serviceDAO = ServiceDAO.getInstance();
        ServiceService service = new ServiceService(serviceDAO);
        MasterDAO masterDAO = MasterDAO.getInstance();
        MasterService master = new MasterService(masterDAO);
        ServiceMasterDAO serviceMasterDAO = ServiceMasterDAO.getInstance();
        ServiceMasterService serviceMaster = new ServiceMasterService(serviceMasterDAO);
        RecordDAO recordDAO = RecordDAO.getInstance();
        RecordService record = new RecordService(recordDAO);


        List<Record> records = record.findPreviousDayRecordsWithoutFeedback();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            users.add(user.findUserById(records.get(i).getUser_id()));
        }
        List<ServiceMaster> mastersService = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            mastersService.add(serviceMaster.findServiceMasterById(records.get(i).getMaster_has_service_id()));
        }
        List<Status> statuses = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            long id = records.get(i).getStatus_id() - 1;
            statuses.add(Status.values()[(int) id]);
        }


        List<Master> masters = new ArrayList<>();
        for (int i = 0; i < mastersService.size(); i++) {
            masters.add(master.findMasterById(mastersService.get(i).getMaster_id()));
        }
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < mastersService.size(); i++) {
            services.add(service.findServiceById(mastersService.get(i).getService_id()));
        }

        List<User> mastersUser = new ArrayList<>();
        for (int i = 0; i < masters.size(); i++) {
            mastersUser.add(user.findUserById(masters.get(i).getUser_id()));
        }

        for (int i = 0; i < records.size(); i++) {
            records.get(i).setUser(users.get(i));
            records.get(i).setUserMaster(mastersUser.get(i));
            records.get(i).setService(services.get(i));
            records.get(i).setServiceMaster(mastersService.get(i));
            records.get(i).setStatus(statuses.get(i));
        }

        if (records.size() > 0) {

            for (int i = 0; i < records.size(); i++) {
                LOGGER.info("Client name " + records.get(i).getUser().getFirstName());
                LOGGER.info("Client email " + records.get(i).getUser().getEmail());
                LOGGER.info("Master name " + records.get(i).getUserMaster().getFirstName());
                LOGGER.info("Master lastname " + records.get(i).getUserMaster().getLastName());
                LOGGER.info("Service name " + records.get(i).getService().getName());
                LOGGER.info("Record id " + records.get(i).getId());

                boolean result = sendEmail(records.get(i).getUser().getEmail(),
                        records.get(i).getUser().getFirstName(),
                        records.get(i).getUserMaster().getFirstName() + " " +
                                records.get(i).getUserMaster().getLastName(),
                        records.get(i).getService().getName(),
                        records.get(i).getId().toString());
                LOGGER.info(result);
            }
        } else {
            LOGGER.info("All previous day records with feedbacks");
        }
    }

    static void emailProcessTest(){
        LOGGER.info("emailProcessTest: " + LocalDateTime.now());
    }
}
