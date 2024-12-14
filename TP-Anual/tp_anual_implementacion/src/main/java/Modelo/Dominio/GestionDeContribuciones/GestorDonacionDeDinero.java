package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.contribucion.DonacionDeDinero;

import Repositories.contribucion.DonacionDeDineroRepository;
import Repositories.colaborador.ColaboradorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Servicios_Externos_APIs.NotificacionService;

@Component
public class GestorDonacionDeDinero {

    private static ColaboradorRepository colaboradorRepository;
    private static DonacionDeDineroRepository donacionDeDineroRepository;
    private static NotificacionService notificacionService;

    @Autowired
    public GestorDonacionDeDinero(ColaboradorRepository colaboradorRepository, DonacionDeDineroRepository donacionDeDineroRepository, NotificacionService notificacionService) {
        this.colaboradorRepository = colaboradorRepository;
        this.donacionDeDineroRepository= donacionDeDineroRepository;
        this.notificacionService = notificacionService;
    }

    public static void crearContribucion(DonacionDeDinero donacionDeDinero){
        donacionDeDinero.procesarLaContribucion();
        colaboradorRepository.save(donacionDeDinero.getColaborador());

        // Notifica al colaborador
        String mensajeNotificacion = "Gracias por la donación de: " + donacionDeDinero.getMonto() + " pesos";
        notificacionService.sendNotificacionToColaborador(donacionDeDinero.getColaborador(), mensajeNotificacion);
    }
}
