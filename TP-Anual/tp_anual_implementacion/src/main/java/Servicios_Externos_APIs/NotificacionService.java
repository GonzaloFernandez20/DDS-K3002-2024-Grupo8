package Servicios_Externos_APIs;

import org.springframework.beans.factory.annotation.Autowired;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.colaborador.Colaborador;
import java.util.List;
public class NotificacionService {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    public void sendNotification(String message) {
        // Obtener todas las personas
        List<Colaborador> colaboradores = colaboradorRepository.findAll();

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
    }
}