package Servicios_Externos_APIs;

import java.util.HashMap;
import java.util.Map;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.Telegram;
import Modelo.Dominio.medios_de_contacto.WhatsApp;

public class NotificacionService {

    private final Map<String, MedioDeContacto> mediosDeContacto;

    public NotificacionService() {
        mediosDeContacto = new HashMap<>();
        // Registrar los medios de contacto disponibles
        mediosDeContacto.put("email", new Mail());
        mediosDeContacto.put("whatsapp", new WhatsApp());
        mediosDeContacto.put("telegram", new Telegram());
    }
    public void sendNotification(String medio,String userId ,String message) {
        MedioDeContacto medioDeContacto = mediosDeContacto.get(medio);

        if (medioDeContacto != null) {
            medioDeContacto.notificar(message, userId);
        } else {
            System.out.println("Medio de contacto no soportado: " + medioDeContacto);
        }
    }
}
