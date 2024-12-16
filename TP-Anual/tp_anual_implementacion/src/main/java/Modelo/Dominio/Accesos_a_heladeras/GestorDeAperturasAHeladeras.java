package Modelo.Dominio.Accesos_a_heladeras;

import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Repositories.Accesos_a_heladeras.AperturaConPermisoRepository;
import Repositories.Accesos_a_heladeras.VinculacionRepository;
import Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GestorDeAperturasAHeladeras {
    private final List<AccesoAHeladeras> tarjetasRegistradas;
    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private final VinculacionRepository vinculacionRepository;
    private final HeladeraRepository heladeraRepository;

    // ------------------------------------------------
    @Autowired
    private GestorDeAperturasAHeladeras(AccesoDeColaboradorRepository accesoDeColaboradorRepository,
                                        VinculacionRepository vinculacionRepository,
                                        HeladeraRepository heladeraRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
        this.heladeraRepository = heladeraRepository;
        this.tarjetasRegistradas = new ArrayList<>();
    }

    // ------------------------------------------------
    public boolean autorizarApertura(String codigoDeTarjeta, int idHeladera) {
        Optional<Heladera> heladera = heladeraRepository.findById(idHeladera);
        if (heladera.isPresent()) {
            Optional<AccesoAHeladeras> acceso = tarjetasRegistradas.stream()
                    .filter(unAcceso -> codigoDeTarjeta.equals(unAcceso.getCodigoTarjeta()))
                    .findFirst();
            if (acceso.isPresent()) {
                return acceso.get().estaAutorizadaLaApertura(heladera.get()); // Chequea si tiene un permiso hecho
            } else
                return false; // Si devuelve false es porque la tarjeta no esta registrada en el sistema, no autorizo que abra la heladera
        }
        else return false;
    }


    //se va al mergear
    public void registrarAccesoDeColaborador(AccesoDeColaborador accesoDeColaborador) {
        accesoDeColaboradorRepository.save(accesoDeColaborador);
    }

    public void registrarAccesoDeVulnerable(Vinculacion vinculacion){
        vinculacionRepository.save(vinculacion);
    }
}

