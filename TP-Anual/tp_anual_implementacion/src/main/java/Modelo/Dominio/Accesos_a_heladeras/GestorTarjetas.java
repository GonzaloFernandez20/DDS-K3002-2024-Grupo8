package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.GestionDeContribuciones.GestorRegistroPersonaVulnerable;
import Repositories.Accesos_a_heladeras.AccesoAHeladerasRepository;
import Repositories.Accesos_a_heladeras.SolicitudTarjetaRepository;
import Repositories.Accesos_a_heladeras.VinculacionRepository;
import Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.colaborador.Colaborador;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GestorTarjetas {

    //Dependencias ----------------------------------------------------------------------------------------------------
    private final GestorRegistroPersonaVulnerable gestorRegistroPersonaVulnerable;
    private final ColaboradorRepository colaboradorRepository;
    private final VinculacionRepository vinculacionRepository;
    private final AccesoAHeladerasRepository accesoAHeladerasRepository;
    private final SolicitudTarjetaRepository solicitudTarjetaRepository;


    @Autowired
    public GestorTarjetas(GestorRegistroPersonaVulnerable gestorRegistroPersonaVulnerable,
                          ColaboradorRepository colaboradorRepository,
                          VinculacionRepository vinculacionRepository, AccesoAHeladerasRepository accesoAHeladerasRepository,
                          SolicitudTarjetaRepository solicitudTarjetaRepository) {
        this.gestorRegistroPersonaVulnerable = gestorRegistroPersonaVulnerable;
        this.colaboradorRepository = colaboradorRepository;
        this.vinculacionRepository = vinculacionRepository;
        this.accesoAHeladerasRepository = accesoAHeladerasRepository;
        this.solicitudTarjetaRepository = solicitudTarjetaRepository;
    }


    //Metodos ----------------------------------------------------------------------------------------------------
    public void registrarVinculacion(Vinculacion vinculacion) {
        validarCodigoDeTarjeta(vinculacion.getCodigoTarjeta());
        log.info("El colaborador ID:{} registro a una persona en situación vulnerable como forma de contribución", vinculacion.getColaboradorQueRegistro().getId_colaborador());

        Vinculacion vinculacionGuardada = vinculacionRepository.save(vinculacion);
        log.info("Se vinculó al sistema la persona vulnerable ID:{} asociada a la tarjeta: {}",
                vinculacionGuardada.getPersonaSituacionVulnerable().getId_persona_en_situacion_vulnerable(),
                vinculacionGuardada.getCodigoTarjeta());

        gestorRegistroPersonaVulnerable.procesarVinculacionPersonaVulnerable(vinculacionGuardada);
    }

    public void registrarAccesoDeColaborador(String codigo, Colaborador colaborador){
        validarCodigoDeTarjeta(codigo);
        AccesoDeColaborador accesoDeColaborador = new AccesoDeColaborador(codigo, colaborador);
        colaborador.setTarjeta(accesoDeColaborador);
        log.info("El colaborador ID:{} ahora tiene acceso a las heladeras asociado al código de tarjeta: {}", colaborador.getId_colaborador(), codigo);
        colaboradorRepository.save(accesoDeColaborador.getColaborador());
    }

    private void validarCodigoDeTarjeta(String codigo) {
        Optional<AccesoAHeladeras> acceso = accesoAHeladerasRepository.findByCodigoTarjeta(codigo);
        if(acceso.isPresent()){
            log.error("Error: se ingresó el código de una tarjeta que ya se encuentra registrada");
            throw new RuntimeException("La tarjeta que intenta registrar pertenece a otra persona.");
        }
        if(codigo.length() != 11 || !codigo.matches("^[a-zA-Z0-9]+$")) {
            log.error("Error: se ingresó un código de tarjeta inválido");
            throw new RuntimeException("Código inválido");
        }
    }

    public void generarSolicitud(Colaborador destinatario, int cantidadDeTarjetas){
        SolicitudTarjeta solicitudTarjeta = new SolicitudTarjeta(destinatario, cantidadDeTarjetas);
        solicitudTarjetaRepository.save(solicitudTarjeta);
    }
}