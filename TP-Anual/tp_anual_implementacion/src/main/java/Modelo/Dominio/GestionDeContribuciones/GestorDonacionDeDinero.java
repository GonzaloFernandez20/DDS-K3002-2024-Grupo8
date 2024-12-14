package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.contribucion.DonacionDeDinero;
import Repositories.contribucion.DonacionDeDineroRepository;
import Repositories.colaborador.ColaboradorRepository;
import org.springframework.stereotype.Component;

@Component
public class GestorDonacionDeDinero {

    private static ColaboradorRepository colaboradorRepository;
    private static DonacionDeDineroRepository donacionDeDineroRepository;


    public GestorDonacionDeDinero(ColaboradorRepository colaboradorRepository, DonacionDeDineroRepository donacionDeDineroRepository) {
        GestorDonacionDeDinero.colaboradorRepository = colaboradorRepository;
        GestorDonacionDeDinero.donacionDeDineroRepository= donacionDeDineroRepository;
    }

    public static void crearContribucion(DonacionDeDinero donacionDeDinero){
        donacionDeDinero.procesarLaContribucion();
        colaboradorRepository.save(donacionDeDinero.getColaborador());
    }
}
