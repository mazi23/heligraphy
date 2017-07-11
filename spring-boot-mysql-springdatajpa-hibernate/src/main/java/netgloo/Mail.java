package netgloo;

import netgloo.models.Bild;
import netgloo.models.daos.BestellungDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mazi on 30.04.17.
 */
@Component
public class Mail {

    @Autowired
    BestellungDao bestellungDao;


    Properties props;
    final String username = "info@heligraphy.at";
    final String password = "info@heligraphy";

    Session session ;
    public Mail() {

        props = new Properties();
        props.put("mail.smtp.host", "smtp.world4you.com");
        //props.put("mail.smtp.socketFactory.port", "25");
        //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.socketFactory.fallback", "true");
        props.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });




    }

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public void sendMail(String subject, String text, HashMap<Integer,Bild> bilder) throws MessagingException, IOException {
        //Adres[] adressen = new Object[]{InternetAddress.parse(to)};
        //try {


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bestellung@heligraphy.at"));

            //Live
           // message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("muenchen@whitewall.de,info@heligraphy.at"));

        //Test
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("info@heligraphy.at"));

            message.setSubject(subject);


            MimeMultipart content = new MimeMultipart();

            MimeBodyPart mainPart = new MimeBodyPart();

            mainPart.setText(text);
            content.addBodyPart(mainPart);


            for (Map.Entry<Integer, Bild> entry : bilder.entrySet()) {
                Bild b = entry.getValue();
                MimeBodyPart imagePart = new MimeBodyPart();

                File outputFile = new File("image" + entry.getKey() + ".jpg");

                FileOutputStream outputStream = new FileOutputStream(outputFile);

                outputStream.write(b.getDatei());  //write the bytes and your done.
                outputStream.close();

                //BufferedImage img = ImageIO.read(new ByteArrayInputStream(b.getDatei()));
                //File outputfile = new File("image"+count+".jpg");
                //ImageIO.write(img, "jpg", outputFile);
                imagePart.attachFile(outputFile);
                content.addBodyPart(imagePart);
            }
            message.setContent(content);

            Transport.send(message);
        /*}catch (Exception e){
            logger.error(e.getMessage());
        }*/
    }


    public void sendMailWithBill(String to,  String text,byte[] pdf,long daosize) throws MessagingException, IOException {
        //Adres[] adressen = new Object[]{InternetAddress.parse(to)};
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("bestellung@heligraphy.at"));
        message.addRecipients(Message.RecipientType.BCC,
                InternetAddress.parse(to+",info@heligraphy.at"));
        message.setSubject("Bestellbestätigung");//"Wir werden uns in kürze bei Ihnen melden. \r\n\r\n "+subject+"---------------------\r\n");
        //message.setText(text);


        MimeMultipart content = new MimeMultipart();

        MimeBodyPart mainPart = new MimeBodyPart();

        mainPart.setText(text);
        content.addBodyPart(mainPart);
        File outputFile;
        if (daosize==0L){
            outputFile = new File("Rechnung.pdf");
        }else{
            outputFile = new File("Rechnung"+daosize+".pdf");
        }

            MimeBodyPart imagePart = new MimeBodyPart();
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(pdf);
            outputStream.close();
            imagePart.attachFile(outputFile);
            content.addBodyPart(imagePart);

        message.setContent(content);

        Transport.send(message);


    }

    public void sendCustomMail(String to, String subject,String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@heligraphy.at"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }


}
