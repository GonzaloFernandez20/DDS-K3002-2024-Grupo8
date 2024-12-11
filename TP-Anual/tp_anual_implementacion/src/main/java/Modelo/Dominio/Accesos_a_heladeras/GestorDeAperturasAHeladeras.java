package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.AperturaConPermisoRepository;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.VinculacionRepository;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GestorDeAperturasAHeladeras {
    private static GestorDeAperturasAHeladeras instancia;
    private final List<AccesoAHeladeras> tarjetasRegistradas;
    private AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private VinculacionRepository vinculacionRepository;

    // ------------------------------------------------
    @Autowired
    private GestorDeAperturasAHeladeras(AccesoDeColaboradorRepository accesoDeColaboradorRepository, VinculacionRepository vinculacionRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
        this.tarjetasRegistradas = new ArrayList<>();

    }

    private GestorDeAperturasAHeladeras() {
        this.tarjetasRegistradas = new ArrayList<>();
    }

    public static GestorDeAperturasAHeladeras getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeAperturasAHeladeras();
        }
        return instancia;
    }
    // ------------------------------------------------
    public boolean autorizarApertura(String codigoDeTarjeta, Heladera heladera){
        Optional <AccesoAHeladeras> acceso = tarjetasRegistradas.stream()
                                                                .filter(unAcceso -> codigoDeTarjeta.equals(unAcceso.getCodigoTarjeta()))
                                                                .findFirst();
        if (acceso.isPresent()){
            return acceso.get().estaAutorizadaLaApertura(heladera); // Chequea si tiene un permiso hecho
        }else return false; // Si devuelve false es porque la tarjeta no esta registrada en el sistema, no autorizo que abra la heladera
    }


    public void registrarAccesoDeColaborador(AccesoDeColaborador accesoDeColaborador) {
        accesoDeColaboradorRepository.save(accesoDeColaborador);
    }

    public void registrarAccesoDeVulnerable(Vinculacion vinculacion){
        vinculacionRepository.save(vinculacion);
    }
}

