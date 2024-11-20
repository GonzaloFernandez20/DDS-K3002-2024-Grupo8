package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import Servicios_Externos_APIs.NotificacionService;

@Controller  
public class CtrlNotificacion {

    private final NotificacionService notificacionService;

    public CtrlNotificacion() { this.notificacionService = new NotificacionService(); }

    @GetMapping("/EnviarNotificacion")
    public void sendNotification(String medio, String userId, String message) { notificacionService.sendNotification(medio, userId, message); }

}
