package Servidor_CDA.Servidor_CDA.notification;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void sendNotification(String to, String subject, String templateName, boolean resultadoRevision, Date fechaRevision) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Establece los detalles del correo
        helper.setTo(to);
        helper.setSubject(subject);

        // Genera el cuerpo del correo usando la plantilla
        String body = emailTemplateService.generateTemplateContent(templateName, resultadoRevision, fechaRevision);
        helper.setText(body, true);

        // Envía el correo
        mailSender.send(message);
    }
}