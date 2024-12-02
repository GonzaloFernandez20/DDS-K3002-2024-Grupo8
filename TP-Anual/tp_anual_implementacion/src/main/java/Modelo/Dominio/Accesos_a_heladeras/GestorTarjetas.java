package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.GestionDeContribuciones.GestorRegistroPersonaVulnerable;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.VinculacionRepository;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestorTarjetas {
    GestorRegistroPersonaVulnerable gestorRegistroPersonaVulnerable;
    ColaboradorRepository colaboradorRepository;
    VinculacionRepository vinculacionRepository;

    @Autowired
    public GestorTarjetas(GestorRegistroPersonaVulnerable gestorRegistroPersonaVulnerable,
                          ColaboradorRepository colaboradorRepository,
                          VinculacionRepository vinculacionRepository) {
        this.gestorRegistroPersonaVulnerable = gestorRegistroPersonaVulnerable;
        this.colaboradorRepository = colaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
    }

    //Metodos ----------------------------------------------------------------------------------------------------
    public void registrarVinculacion(Vinculacion vinculacion) {
        try {
            Vinculacion vinculacionGuardada = vinculacionRepository.save(vinculacion);
            gestorRegistroPersonaVulnerable.procesarVinculacionPersonaVulnerable(vinculacionGuardada);
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("La tarjeta que intenta registrar pertenece a otra persona.");
        }
    }

    public void registrarAccesoDeColaborador(AccesoDeColaborador accesoDeColaborador){
        try {
            colaboradorRepository.save(accesoDeColaborador.getColaborador());
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("La tarjeta que intenta registrar pertenece a otra persona.");
        }
    }
}
