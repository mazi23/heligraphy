package netgloo.models.Service;

import netgloo.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by mazi on 08.07.17.
 */
@Service
public class MailClient {

    private JavaMailSender mailSender;
    private MailContentBuilder mailContentBuilder;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Autowired
    public MailClient(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void prepareAndSend(String recipient,String subject, String message, byte[] rechnung, long RENummer) {

        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
                messageHelper.setFrom("info@heligraphy.at");
                messageHelper.setTo(new String[]{recipient,"info@heligraphy.at"});

                messageHelper.setSubject(subject);
                String content = mailContentBuilder.build(message);

                File outputFile = new File("Rechnung.pdf");

                FileOutputStream outputStream = new FileOutputStream(outputFile);

                outputStream.write(rechnung);  //write the bytes and your done.
                outputStream.close();

                messageHelper.addAttachment("Rechnung"+RENummer+".pdf",outputFile);
                messageHelper.setText(content, true);
            };
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            logger.error(e.getMessage());
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void prepareAndSend(String recipient,String subject, String message) {

        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
                messageHelper.setFrom("info@heligraphy.at");
                messageHelper.setTo(recipient);
                messageHelper.setSubject(subject);
                String content = mailContentBuilder.build(message);


                messageHelper.setText(content, true);
            };
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            logger.error(e.getMessage());
            // runtime exception; compiler will not force you to handle it
        }
    }

}
