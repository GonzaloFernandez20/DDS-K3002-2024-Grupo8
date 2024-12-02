package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.contribucion.RegistroDePersonaVulnerable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GestorRegistroPersonaVulnerable {
    ColaboradorRepository colaboradorRepository;

    @Autowired
    public GestorRegistroPersonaVulnerable(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    //Metodos ----------------------------------------------------------------------------------------------------------------
    public void procesarVinculacionPersonaVulnerable(Vinculacion vinculacion) {
        RegistroDePersonaVulnerable nuevaContribucion = new RegistroDePersonaVulnerable(vinculacion.getColaboradorQueRegistro(),
                                                                                       vinculacion,
                                                                                       LocalDate.now());
        nuevaContribucion.procesarLaContribucion();
        colaboradorRepository.save(vinculacion.getColaboradorQueRegistro());
    }
}
