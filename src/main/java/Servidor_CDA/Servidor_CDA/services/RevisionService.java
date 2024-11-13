package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.notification.EmailService;
import Servidor_CDA.Servidor_CDA.notification.NotificationScheduler;
import Servidor_CDA.Servidor_CDA.notification.NotificationTask;
import Servidor_CDA.Servidor_CDA.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.*;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationScheduler notificationScheduler;

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



    public void processRevisionNotification(Revision revision) {
        try {
            boolean resultadoRevision = revision.isResultadoRevision();
            String email = revision.getVehiculo().getUsuario().getCorreo();

            // Configurar los delays (para pruebas: 5s y 15s)
            long delayAprobado = 5 * 1000L; // 5 segundos (cambiar a 9 meses en producción)
            long delayReprobado = 15 * 1000L; // 15 segundos (cambiar a 15 días en producción)
            long delay = resultadoRevision ? delayAprobado : delayReprobado;

            // Crear la tarea de notificación
            NotificationTask task = new NotificationTask();
            task.setTo(email);
            task.setEmail(email);
            task.setSubject("Recordatorio de revisión tecnomecánica");
            task.setResultadoRevision(resultadoRevision);
            task.setFechaRevision(new Date());
            task.setScheduledTime(new Date(System.currentTimeMillis() + delay));

            // Programar la notificación
            notificationScheduler.scheduleNotification(task);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
