package Controladores.Contribuciones;

import DTOs.DistribucionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Excepciones.ExcepcionNoHayEspacioEnDestino;
import Modelo.Excepciones.ExcepcionViandasInsuficientesEnOrigen;
import Modelo.Mappers.DistribucionDeViandasMapper;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Repositorios.RepositorioHeladeras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CtrlDistribucionViandas {

    private final HeladeraRepository repositorioHeladeras;
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlDistribucionViandas(HeladeraRepository repositorioHeladeras, GestorInicioDeSesion gestorInicioDeSesion) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
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
        //if(Objects.isNull(colaborador.getTarjeta())) {
         //   return "PedirTarjetaColaborador";
        //}

        //PENDIENTE PARA PERSISTENCIA
        heladeras = repositorioHeladeras.findAll().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());

        /*heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());
*/
        model.addAttribute("heladeras", heladeras);
        this.setMotivos();
        model.addAttribute("motivos", motivos);

        return "DistribuirVianda";
    }

    @PostMapping("/DistribuirVianda")
    public ResponseEntity<String> procesarSolicitudDistribucion(@RequestBody DistribucionDeViandaDTO distribucionDTO) {
        System.out.println(distribucionDTO.getMotivoDeDistribucion() + ' ' + distribucionDTO.getCantidadDeViandas() + ' ' + distribucionDTO.getHeladeraDeOrigenID() + ' ' + distribucionDTO.getHeladeraDestinoID());

        try{
            DistribucionDeViandas nuevaDistribucion = procesarDTO(distribucionDTO);

            System.out.println(nuevaDistribucion.getMotivoDeDistribucion().toString() + ' ' + nuevaDistribucion.getCantidadDeViandasAMover() + ' ' + nuevaDistribucion.getHeladeraDeOrigen().getUbicacion().getNombreCompletoDeUbicacion() + ' ' + nuevaDistribucion.getHeladeraDestino().getUbicacion().getNombreCompletoDeUbicacion());

            GestorDePermisosDeApertura.generarPermisosDeDistribucion(nuevaDistribucion);
            return ResponseEntity.ok("La declaración de la distribución se ha realizado con éxito!");
        } catch (ExcepcionViandasInsuficientesEnOrigen e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad de viandas en la heladera origen es insuficiente.");
        } catch (ExcepcionNoHayEspacioEnDestino e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay espacio suficiente en la heladera destino.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error desconocido.");
        }
    }

    private DistribucionDeViandas procesarDTO(DistribucionDeViandaDTO dto){
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
        // HARDCODEADO POR MOTIVOS DIDÁCTICOS,AL IGUAL QUE EL COMENTARIO EN PEDIRLE TARJETA AL COLABORADOR
        AccesoDeColaborador accesoDeColaborador = new AccesoDeColaborador("1010", colaborador);
        colaborador.setTarjeta(accesoDeColaborador);

        Optional<Heladera> heladeraOrigenElegida = repositorioHeladeras.findById(dto.getHeladeraDeOrigenID());
        Optional<Heladera> heladeraDestinoElegida = repositorioHeladeras.findById(dto.getHeladeraDestinoID());
        DistribucionDeViandas nuevaDistribucion = DistribucionDeViandasMapper.crearDistribucionAPartirDe(dto,
                                                                                                         heladeraOrigenElegida.get(),
                                                                                                         heladeraDestinoElegida.get(),
                                                                                                            colaborador);


        return nuevaDistribucion;
    }

}
