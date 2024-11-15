package Servidor_CDA.Servidor_CDA.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public void sendNotificationWithAttachment(String to, String subject, String templateName,
                                               boolean resultadoRevision, Date fechaRevision, byte[] pdfAttachment) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        String body = emailTemplateService.generateTemplateContent(templateName, resultadoRevision, fechaRevision);
        helper.setText(body, true);

        // Adjuntar el PDF al correo
        helper.addAttachment("Certificado_Tecnico_Mecanica.pdf", new ByteArrayResource(pdfAttachment));

        mailSender.send(message);
    }
}
