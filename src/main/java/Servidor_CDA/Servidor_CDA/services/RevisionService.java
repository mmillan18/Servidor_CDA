package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.notification.EmailService;
import Servidor_CDA.Servidor_CDA.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private EmailService emailService;

    // Método para obtener todas las revisiones
    public List<Revision> getAllRevisions() {
        return revisionRepository.findAll();
    }

    // Método para obtener una revisión por su ID
    public Revision getRevisionById(Long id) {
        return revisionRepository.findById(id).orElse(null);
    }

    // Método para crear una nueva revisión y programar la notificación automáticamente
    public Revision createRevision(Revision revision) {
        Revision savedRevision = revisionRepository.save(revision);
        processRevisionNotification(savedRevision); // Llamada al método de notificación
        return savedRevision;
    }

    // Método para eliminar una revisión
    public void deleteRevision(Long id) {
        revisionRepository.deleteById(id);
    }

    // Método para programar la notificación por correo según el resultado de la revisión
    public void processRevisionNotification(Revision revision) {
        try {
            boolean resultadoRevision = revision.isResultadoRevision(); // True si es aprobada, false si es reprobada
            String email = revision.getVehiculo().getUsuario().getCorreo();

            // Configuración de tiempos en milisegundos (5 segundos y 15 segundos)
            long delayAprobado = 5 * 1000L; // 5 segundos
            long delayReprobado = 15 * 1000L; // 15 segundos
            long delay = resultadoRevision ? delayAprobado : delayReprobado;

            // Programar el envío del correo en el tiempo especificado
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        emailService.sendNotification(
                                email,
                                "Recordatorio de revisión tecnomecánica",
                                "notificationTemplate.html",
                                resultadoRevision,
                                new Date() // Fecha actual de envío
                        );
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }, delay); // Utilizamos el delay calculado anteriormente

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
