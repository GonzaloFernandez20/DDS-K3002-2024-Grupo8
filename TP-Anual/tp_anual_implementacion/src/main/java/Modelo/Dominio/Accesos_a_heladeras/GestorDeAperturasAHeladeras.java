package Modelo.Dominio.Accesos_a_heladeras;

import Repositories.Accesos_a_heladeras.AccesoAHeladerasRepository;
import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Repositories.Accesos_a_heladeras.VinculacionRepository;
import Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GestorDeAperturasAHeladeras {
   // Dependencias ---------------------------------------------------------------------------
    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private final VinculacionRepository vinculacionRepository;
    private final HeladeraRepository heladeraRepository;
    private final AccesoAHeladerasRepository accesoAHeladerasRepository;

    @Autowired
    private GestorDeAperturasAHeladeras(AccesoDeColaboradorRepository accesoDeColaboradorRepository,
                                        VinculacionRepository vinculacionRepository,
                                        HeladeraRepository heladeraRepository, AccesoAHeladerasRepository accesoAHeladerasRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
        this.heladeraRepository = heladeraRepository;
        this.accesoAHeladerasRepository = accesoAHeladerasRepository;
    }

    // Metodos ---------------------------------------------------------------------------------------------------
    public boolean autorizarApertura(String codigoDeTarjeta, int idHeladera) {
        Optional<Heladera> heladera = heladeraRepository.findById(idHeladera);
        Optional<AccesoAHeladeras> acceso = accesoAHeladerasRepository.findByCodigoTarjeta(codigoDeTarjeta);

        if (heladera.isPresent() && acceso.isPresent()) {
            boolean estaAutorizada = acceso.get().estaAutorizadaLaApertura(heladera.get());
            if (estaAutorizada) {registrarApertura(acceso.get());}
            return estaAutorizada;
        } else return false;
    }

    private void registrarApertura(AccesoAHeladeras acceso) {
        if(acceso instanceof Vinculacion vinculacionActualizada){
           vinculacionRepository.save(vinculacionActualizada);
        }
        else{
            AccesoDeColaborador accesoActualizada = (AccesoDeColaborador) acceso;
            accesoDeColaboradorRepository.save(accesoActualizada);
        }
    }
}

