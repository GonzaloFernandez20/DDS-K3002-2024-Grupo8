import java.util.HashMap;
import java.util.Map;

public class NotificacionService {

    private final Map<String, MedioDeContacto> mediosDeContacto;

    public NotificacionService() {
        mediosDeContacto = new HashMap<>();
        // Registrar los medios de contacto disponibles
        mediosDeContacto.put("email", new Email());
        mediosDeContacto.put("whatsapp", new WhatsApp());
        mediosDeContacto.put("telegram", new Telegram());
    }
    public void sendNotification(String medio, String message) {
        MedioDeContacto medioDeContacto = mediosDeContacto.get(medio);

        if (medioDeContacto != null) {
            medioDeContacto.send(userId, recipient, message);
        } else {
            System.out.println("Medio de contacto no soportado: " + medioDeContacto);
        }
    }
}
