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



    @RequestMapping("/sendMail")
    public String send(@ModelAttribute(value = "contactCommand") ContactCommand contactCommand, Model model, BindingResult bindingResult){

        sendMail(contactCommand.getMail(),contactCommand.getBetreff(),contactCommand.getNachricht(),contactCommand.getTelefon());

        return "services-2";
    }

    public void sendMail(String to,String subject, String text,String telefon)
    {
        final String username = "info@heligraphy.at";
        final String password = "info@heligraphy.at";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.world4you.com");
        //props.put("mail.smtp.socketFactory.port", "25");
        //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            //Object[] adressen = new Object[]{InternetAddress.parse("mac.matthias@gmail.com"),InternetAddress.parse(from)};
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@heligraphy.at"));
            message.addRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(to+",info@heligraphy.at"));

            message.setSubject("Wir werden uns in kürze bei Ihnen melden");
            message.setText("Ihre Anfrage \n"+"---------------------\r\n"+subject+ "\nTelefon: "+telefon+"\n" +text);

            Transport.send(message);




        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
