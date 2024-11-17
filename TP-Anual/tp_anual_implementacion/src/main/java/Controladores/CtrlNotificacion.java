package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  
public class CtrlNotificacion {

    private final NotificacionService notificacionService;

    public CtrlNotificacion() { this.notificacionService = new NotificacionService(); }

    @GetMapping("/EnviarNotificacion")
    public void sendNotification(String medio, String userId, String recipient, String message) { notificacionService.sendNotification(medio, userId, recipient, message); }

}
