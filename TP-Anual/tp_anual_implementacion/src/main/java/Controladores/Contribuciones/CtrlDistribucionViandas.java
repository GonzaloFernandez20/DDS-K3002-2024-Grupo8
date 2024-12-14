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

    private final HeladeraRepository repositorioHeladeras;
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private final AperturaConPermisoRepository aperturaConPermisoRepository;
    private final DistribucionDeViandaRepository distribucionDeViandaRepository;

    @Autowired
    public CtrlDistribucionViandas(HeladeraRepository repositorioHeladeras, GestorInicioDeSesion gestorInicioDeSesion, AccesoDeColaboradorRepository accesoDeColaboradorRepository, AperturaConPermisoRepository aperturaConPermisoRepository, DistribucionDeViandaRepository distribucionDeViandaRepository) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.aperturaConPermisoRepository = aperturaConPermisoRepository;
        this.distribucionDeViandaRepository = distribucionDeViandaRepository;
    }

    private List<HeladeraSeleccionDTO> heladeras;

    List<MotivoDeDistribucion> motivos = new ArrayList<>();

    public void setMotivos() {
        if(motivos.isEmpty()) {
            motivos.add(MotivoDeDistribucion.FALTA_DE_VIANDAS);
            motivos.add(MotivoDeDistribucion.DESPERFECTO_HELADERA);
        }
    }

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

    @Transactional
    @PostMapping("/DistribuirVianda")
    public ResponseEntity<String> procesarSolicitudDistribucion(@RequestBody DistribucionDeViandaDTO distribucionDTO) {
        System.out.println(distribucionDTO.getMotivoDeDistribucion() + ' ' + distribucionDTO.getCantidadDeViandas() + ' ' + distribucionDTO.getHeladeraDeOrigenID() + ' ' + distribucionDTO.getHeladeraDestinoID());

        try{
            DistribucionDeViandas nuevaDistribucion = procesarDTO(distribucionDTO);

            System.out.println(nuevaDistribucion.getMotivoDeDistribucion().toString() + ' ' + nuevaDistribucion.getCantidadDeViandasAMover() + ' ' + nuevaDistribucion.getHeladeraDeOrigen().getUbicacion().getNombreCompletoDeUbicacion() + ' ' + nuevaDistribucion.getHeladeraDestino().getUbicacion().getNombreCompletoDeUbicacion());

            // NO CAMBIAN DE HELADERA HASTA QUE SE EFECTÚE LA DISTRIBUCIÓN
            distribucionDeViandaRepository.save(nuevaDistribucion);

            GestorDePermisosDeApertura gestorDePermisosDeApertura = new GestorDePermisosDeApertura(accesoDeColaboradorRepository, aperturaConPermisoRepository);
            gestorDePermisosDeApertura.generarPermisosDeDistribucion(nuevaDistribucion);
            return ResponseEntity.ok("La declaración de la distribución se ha realizado con éxito!");
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
        DistribucionDeViandas nuevaDistribucion = DistribucionDeViandasMapper.crearDistribucionAPartirDe(dto,
                                                                                                         heladeraOrigenElegida.get(),
                                                                                                         heladeraDestinoElegida.get(),
                                                                                                            colaborador);


        return nuevaDistribucion;
    }

}
