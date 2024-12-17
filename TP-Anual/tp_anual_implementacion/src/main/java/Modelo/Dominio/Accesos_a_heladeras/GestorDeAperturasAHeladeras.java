package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.contribucion.Vianda;
import Repositories.Accesos_a_heladeras.AccesoAHeladerasRepository;
import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Repositories.Accesos_a_heladeras.VinculacionRepository;
import Repositories.contribucion.ViandaRepository;
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
    private final ViandaRepository viandaRepository;

    @Autowired
    private GestorDeAperturasAHeladeras(AccesoDeColaboradorRepository accesoDeColaboradorRepository,
                                        VinculacionRepository vinculacionRepository,
                                        HeladeraRepository heladeraRepository, AccesoAHeladerasRepository accesoAHeladerasRepository, ViandaRepository viandaRepository) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
        this.heladeraRepository = heladeraRepository;
        this.accesoAHeladerasRepository = accesoAHeladerasRepository;
        this.viandaRepository = viandaRepository;
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
            Vianda viandaRetirada = viandaRepository.save(vinculacionActualizada.getViandasRetiradas().remove(0));
            vinculacionActualizada.getViandasRetiradas().add(viandaRetirada);
            vinculacionRepository.save(vinculacionActualizada);
        }
        else{
            AccesoDeColaborador accesoActualizada = (AccesoDeColaborador) acceso;
            accesoDeColaboradorRepository.save(accesoActualizada);
        }
    }
}

