package Servidor_CDA.Servidor_CDA.notification;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class NotificationScheduler {

    @Autowired
    private EmailService emailService;

    private ThreadPoolTaskScheduler taskScheduler;
    private Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.initialize();
    }

    public void scheduleNotification(NotificationTask task) {
        String taskId = task.getEmail() + "_" + task.getScheduledTime().getTime();

        Runnable runnable = () -> {
            try {
                emailService.sendNotificationWithAttachment(
                        task.getTo(),
                        task.getSubject(),
                        "notificationTemplate",
                        task.isResultadoRevision(),
                        task.getFechaRevision(),
                        task.getPdfAttachment()  // Asegúrate de que `NotificationTask` tenga el campo `pdfAttachment`
                );
                // Remover la tarea después de su ejecución
                scheduledTasks.remove(taskId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(runnable, task.getScheduledTime());
        scheduledTasks.put(taskId, scheduledFuture);
    }
}
