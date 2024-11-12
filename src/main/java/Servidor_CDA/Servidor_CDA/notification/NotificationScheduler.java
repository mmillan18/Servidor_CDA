// NotificationScheduler.java
package Servidor_CDA.Servidor_CDA.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
                emailService.sendNotification(
                        task.getTo(),
                        task.getSubject(),
                        "notificationTemplate",
                        task.isResultadoRevision(),
                        task.getFechaRevision()
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