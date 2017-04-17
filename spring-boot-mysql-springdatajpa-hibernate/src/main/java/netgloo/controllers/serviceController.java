package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class serviceController {
    @RequestMapping({"/services-2"})
    public String start(Model model){
        return "services-2";
    }



    public void sendMail()
    {
        final String username = "grieskirchenMeldungen@gmail.com";
        final String password = "grieskirchenMeldungen1";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("grieskirchenMeldungen@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("mac.matthias@gmail.com"));
            //message.setRecipients(Message.RecipientType.TO,
            //      InternetAddress.parse(problem.getEmail()));
            message.setSubject("");
            message.setText("");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
