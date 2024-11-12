package Servidor_CDA.Servidor_CDA.notification;

import lombok.Data;
import java.util.Date;

@Data
public class NotificationTask {
    private String to;
    private String email;
    private String subject;
    private boolean resultadoRevision;
    private Date fechaRevision;
    private Date scheduledTime;
}