package Servidor_CDA.Servidor_CDA.notification;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@Service
public class EmailTemplateService {

    private final TemplateEngine templateEngine;

    public EmailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateTemplateContent(String templateName, boolean resultadoRevision, Date fechaRevision) {
        Context context = new Context();
        context.setVariable("resultadoRevision", resultadoRevision);
        context.setVariable("fechaRevision", fechaRevision);

        // Genera el contenido de la plantilla
        return templateEngine.process(templateName, context);
    }
}
