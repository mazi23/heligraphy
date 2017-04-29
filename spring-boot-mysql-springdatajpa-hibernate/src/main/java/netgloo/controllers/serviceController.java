package netgloo.controllers;

import netgloo.comands.ContactCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by mazi on 16.04.17.
 */
@Controller
public class serviceController {
    @RequestMapping({"/services-2"})
    public String start(Model model){

        model.addAttribute("contactCommand",new ContactCommand());
        return "services-2";
    }



    @RequestMapping("/send")
    public String send(@ModelAttribute(value = "contactCommand") ContactCommand contactCommand, Model model, BindingResult bindingResult){

        sendMail(contactCommand.getMail(),contactCommand.getBetreff(),contactCommand.getNachricht(),contactCommand.getTelefon());

        return "services-2";
    }

    public void sendMail(String from,String subject, String text,String telefon)
    {
        final String username = "mac.matthias@gmail.com";
        final String password = "macbook1";

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
            Object[] adressen = new Object[]{InternetAddress.parse("mac.matthias@gmail.com"),InternetAddress.parse(from)};
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, (Address[]) adressen);
            message.setSubject("Wir werden uns in kürze bei Ihnen melden. \r\n\r\n "+subject+"---------------------\r\n"+ "Telefon: "+telefon);
            message.setText(text);

            Transport.send(message);




        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
