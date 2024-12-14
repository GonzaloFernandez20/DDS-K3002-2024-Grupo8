package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.GestionDeContribuciones.GestorRegistroPersonaVulnerable;
import Modelo.Dominio.Persona.PersonaHumana;
import Repositories.Accesos_a_heladeras.VinculacionRepository;
import Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.colaborador.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GestorTarjetas {
    private GestorRegistroPersonaVulnerable gestorRegistroPersonaVulnerable;
    private ColaboradorRepository colaboradorRepository;
    private VinculacionRepository vinculacionRepository;
    private List<SolicitudTarjeta> tarjetasPendientesDeEntrega;
    private List<AccesoAHeladeras> tarjetasRegistradas;

    @Autowired
    public GestorTarjetas(GestorRegistroPersonaVulnerable gestorRegistroPersonaVulnerable,
                          ColaboradorRepository colaboradorRepository,
                          VinculacionRepository vinculacionRepository) {
        this.gestorRegistroPersonaVulnerable = gestorRegistroPersonaVulnerable;
        this.colaboradorRepository = colaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
        this.tarjetasPendientesDeEntrega = new ArrayList<>();
    }

    //Metodos ----------------------------------------------------------------------------------------------------
    public void registrarVinculacion(Vinculacion vinculacion) {
        Optional<Vinculacion> codTarjeta = vinculacionRepository.findByCodigoTarjeta(vinculacion.getCodigoTarjeta());
        if (codTarjeta.isEmpty()){
            Vinculacion vinculacionGuardada = vinculacionRepository.save(vinculacion);
            gestorRegistroPersonaVulnerable.procesarVinculacionPersonaVulnerable(vinculacionGuardada);
        }else {
            throw new RuntimeException("La tarjeta que intenta registrar pertenece a otra persona.");
        }
    }

    public void registrarAccesoDeColaborador(String codigo, Colaborador colaborador){
        try {
            AccesoDeColaborador accesoDeColaborador = new AccesoDeColaborador(codigo, colaborador);
            colaborador.setTarjeta(accesoDeColaborador);

            colaboradorRepository.save(accesoDeColaborador.getColaborador());
        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("La tarjeta que intenta registrar pertenece a otra persona.");
        }
    }

    public void generarSolicitud(Colaborador destinatario, int cantidadDeTarjetas){
        SolicitudTarjeta solicitudTarjeta = new SolicitudTarjeta(destinatario, cantidadDeTarjetas);
        tarjetasPendientesDeEntrega.add(solicitudTarjeta);
    }
}