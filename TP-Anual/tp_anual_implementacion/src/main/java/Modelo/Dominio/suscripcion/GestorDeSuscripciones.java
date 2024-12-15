package Modelo.Dominio.suscripcion;

import Modelo.Dominio.Repositories.Suscripciones.NotificadorDeSuscriptosRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorDeSuscripciones {
       private NotificadorDeSuscriptosRepository notificadorDeSuscriptosRepository;

       @Autowired
        public GestorDeSuscripciones(NotificadorDeSuscriptosRepository notificadorDeSuscriptosRepository) {
                this.notificadorDeSuscriptosRepository = notificadorDeSuscriptosRepository;
        }

        //Metodos -----------------------------------------------------------------------------------------------
        public void registrarSuscripcion(Heladera heladera,
                                         Colaborador colaborador,
                                         String evento) {
        NotificadorDeSuscriptos notificador = heladera.getNotificadorDeSuscriptos();
        notificador.suscribir(evento,colaborador);
        }

        public  void efectuarDesuscripcion(Heladera heladera,
                                         Colaborador colaborador,
                                         String evento) {
            NotificadorDeSuscriptos notificador = heladera.getNotificadorDeSuscriptos();
            notificador.desuscribir(evento,colaborador);
            notificadorDeSuscriptosRepository.save(notificador);
        }

        public void guardarSuscripciones(Heladera heladera){
            NotificadorDeSuscriptos notificador = heladera.getNotificadorDeSuscriptos();
            notificadorDeSuscriptosRepository.save(notificador);
        }
}
