package Servicios_Externos_APIs;

import org.springframework.stereotype.Service;

import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.colaborador.Colaborador;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificacionService {

    // Para comunicacion masiva
    /*@Autowired
    ColaboradorRepository colaboradorRepository;

    public void sendNotification(String message) {
        // Obtener todas las personas
        List<Colaborador> colaboradores = colaboradorRepository.findAll();
        if (colaboradores.isEmpty()) {
            System.out.println("No hay colaboradores para notificar.");
            return;
        }
        for (Colaborador colaborador : colaboradores) {
            // Iterar sobre los medios de contacto de la persona
            for (MedioDeContacto medio : colaborador.getMediosDeContacto()) {
                try {
                    medio.notificar(message);
                } catch (Exception e) {
                    System.err.println("Error al notificar a través de " + medio.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        }
    }*/
    
    // Para comunicacion puntual
    @Transactional
    public void sendNotificacionToColaborador(Colaborador colaborador, String message) {
        // Iterar sobre los medios de contacto de la persona
        for (MedioDeContacto medio : colaborador.getMediosDeContacto()) {
            try {
                System.out.println("Notificando a través de " + medio.getClass().getSimpleName() + ": " + message);
                medio.notificar(message);
            } catch (Exception e) {
                System.err.println("Error al notificar a través de " + medio.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
}
