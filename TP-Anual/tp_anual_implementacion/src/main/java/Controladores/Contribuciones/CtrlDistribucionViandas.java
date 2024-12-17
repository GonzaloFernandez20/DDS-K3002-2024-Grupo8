package Controladores.Contribuciones;

import DTOs.DistribucionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Persona.PersonaJuridica;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionNoHayEspacioEnDestino;
import Modelo.Excepciones.ExcepcionViandasInsuficientesEnOrigen;
import Modelo.Mappers.DistribucionDeViandasMapper;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.seguridad.GestorInicioDeSesion;

import Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Repositories.Accesos_a_heladeras.AperturaConPermisoRepository;
import Repositories.contribucion.DistribucionDeViandaRepository;
import Repositories.heladera.HeladeraRepository;

import Servicios_Externos_APIs.NotificacionService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CtrlDistribucionViandas {

    //Dependencias ------------------------------------------------------------------------------------------------------
    private final HeladeraRepository repositorioHeladeras;
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private final AperturaConPermisoRepository aperturaConPermisoRepository;
    private final DistribucionDeViandaRepository distribucionDeViandaRepository;
    private final GestorDePermisosDeApertura gestorDePermisosDeApertura;

    @Autowired
    public CtrlDistribucionViandas(HeladeraRepository repositorioHeladeras,
                                   GestorInicioDeSesion gestorInicioDeSesion,
                                   AccesoDeColaboradorRepository accesoDeColaboradorRepository,
                                   AperturaConPermisoRepository aperturaConPermisoRepository,
                                   DistribucionDeViandaRepository distribucionDeViandaRepository,
                                   GestorDePermisosDeApertura gestorDePermisosDeApertura) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.aperturaConPermisoRepository = aperturaConPermisoRepository;
        this.distribucionDeViandaRepository = distribucionDeViandaRepository;
        this.gestorDePermisosDeApertura = gestorDePermisosDeApertura;
    }


    //GET MAPPING -----------------------------------------------------------------------------------------------------
    @GetMapping("/DistribuirVianda")
    public String mostrarFormulario(Model model) {
        Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();
        if (colaboradorActual.getPersona() instanceof PersonaJuridica) {
            return "PedirRegistroHumano";
        }

        if(Objects.isNull(gestorInicioDeSesion.obtenerColaboradorPorID().getTarjeta())) {
            return "PedirTarjetaColaborador";
        }

        heladeras = repositorioHeladeras.traerHeladerasActivasEnElSistema().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());

        model.addAttribute("heladeras", heladeras);
        this.setMotivos();
        model.addAttribute("motivos", motivos);

        return "DistribuirVianda";
    }
    private List<HeladeraSeleccionDTO> heladeras;

    List<MotivoDeDistribucion> motivos = new ArrayList<>();

    public void setMotivos() {
        if(motivos.isEmpty()) {
            motivos.add(MotivoDeDistribucion.FALTA_DE_VIANDAS);
            motivos.add(MotivoDeDistribucion.DESPERFECTO_HELADERA);
        }
    }

    //POST MAPPING
    @Transactional
    @PostMapping("/DistribuirVianda")
    public ResponseEntity<String> procesarSolicitudDistribucion(@RequestBody DistribucionDeViandaDTO distribucionDTO) {
        try{
            DistribucionDeViandas nuevaDistribucion = procesarDTO(distribucionDTO);
            gestorDePermisosDeApertura.generarPermisosDeDistribucion(nuevaDistribucion);
            return ResponseEntity.ok("Permiso de distribución de viandas generado con éxito, tiene 3 horas para trasladar las viandas antes de que venza el permiso de apertura!");
        } catch (ExcepcionViandasInsuficientesEnOrigen e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad de viandas en la heladera origen es insuficiente.");
        } catch (ExcepcionNoHayEspacioEnDestino e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay espacio suficiente en la heladera destino.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error desconocido.");
        }
    }

    private DistribucionDeViandas procesarDTO(DistribucionDeViandaDTO dto){
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        Optional<Heladera> heladeraOrigenElegida = repositorioHeladeras.findById(dto.getHeladeraDeOrigenID());
        Optional<Heladera> heladeraDestinoElegida = repositorioHeladeras.findById(dto.getHeladeraDestinoID());
        DistribucionDeViandas nuevaDistribucion = DistribucionDeViandasMapper.crearDistribucionAPartirDe(dto,heladeraOrigenElegida.get(), heladeraDestinoElegida.get(), colaborador);

        return nuevaDistribucion;
    }

}
