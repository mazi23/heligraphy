package netgloo;

import netgloo.models.Bild;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mazi on 30.04.17.
 */
public class Mail {

    private String absenderMail;

    private String druckereiMail;
    Properties props;
    final String username = "mac.matthias@gmail.com";
    final String password = "macbook1";

    Session session ;
    public Mail() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        absenderMail = "info@heligraphy.at";
    }

    public void sendMail(String subject, String text, HashMap<Integer,Bild> bilder) throws MessagingException, IOException {
        //Adres[] adressen = new Object[]{InternetAddress.parse(to)};
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@heligraphy.at"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(druckereiMail));//(Address[]) adressen);
        message.setSubject(subject);//"Wir werden uns in kürze bei Ihnen melden. \r\n\r\n "+subject+"---------------------\r\n");
        //message.setText(text);


        MimeMultipart content = new MimeMultipart();

        MimeBodyPart mainPart = new MimeBodyPart();

        mainPart.setText(text);
        content.addBodyPart(mainPart);


        for (Map.Entry<Integer, Bild> entry: bilder.entrySet())
        {
            Bild b = entry.getValue();
            MimeBodyPart imagePart = new MimeBodyPart();

            File outputFile = new File("image"+entry.getKey()+".jpg");

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
    }


    public void sendMailWithBill(String to,  String text,String pdf) throws MessagingException, IOException {
        //Adres[] adressen = new Object[]{InternetAddress.parse(to)};
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("mac.matthias@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));//(Address[]) adressen);
        message.setSubject("Bestellbestätigung");//"Wir werden uns in kürze bei Ihnen melden. \r\n\r\n "+subject+"---------------------\r\n");
        //message.setText(text);


        MimeMultipart content = new MimeMultipart();

        MimeBodyPart mainPart = new MimeBodyPart();

        mainPart.setText(text);
        content.addBodyPart(mainPart);


            MimeBodyPart imagePart = new MimeBodyPart();
            //jasper PDF hier einfügen

            //imagePart.attachFile(outputFile);
            content.addBodyPart(imagePart);

        message.setContent(content);

        Transport.send(message);
    }

    public String getAbsenderMail() {
        return absenderMail;
    }

    public void setAbsenderMail(String absenderMail) {
        this.absenderMail = absenderMail;
    }

    public String getDruckereiMail() {
        return druckereiMail;
    }

    public void setDruckereiMail(String druckereiMail) {
        this.druckereiMail = druckereiMail;
    }
}
