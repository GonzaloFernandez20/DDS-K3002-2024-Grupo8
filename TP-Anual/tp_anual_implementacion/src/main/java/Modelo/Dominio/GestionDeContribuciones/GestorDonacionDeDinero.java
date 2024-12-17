package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.contribucion.DonacionDeDinero;

import Repositories.colaborador.ColaboradorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorDonacionDeDinero {
    //Dependencias -----------------------------------------------------
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public GestorDonacionDeDinero(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public void procesarDineroDonado(DonacionDeDinero donacionDeDinero){
        donacionDeDinero.procesarLaContribucion();
        colaboradorRepository.save(donacionDeDinero.getColaborador());
    }
}
