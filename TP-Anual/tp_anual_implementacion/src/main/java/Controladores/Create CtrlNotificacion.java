package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Servicios_Externos_APIs.NotificacionService;

@Controller  
public class CtrlNotificacion {

    private final NotificacionService notificacionService;

    public CtrlNotificacion() { this.notificacionService = new NotificacionService(); }

    @PostMapping("/EnviarNotificacion")
    public void sendNotification(@RequestParam String message) {
         notificacionService.sendNotification(message);
    }

}