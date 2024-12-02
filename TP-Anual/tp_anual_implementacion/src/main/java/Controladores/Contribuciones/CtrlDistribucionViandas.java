package Controladores.Contribuciones;

import DTOs.DistribucionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
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

import java.time.LocalDate;
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

    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200")), List.of(new WhatsApp("15 2350-2350")));

    //TODO 2: usar repo que se conecta a bd.
    private final List<HeladeraSeleccionDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().
            map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());


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
        model.addAttribute("heladeras", heladeras);
        this.setMotivos();
        model.addAttribute("motivos", motivos);

        return "DistribuirVianda";
    }

    @PostMapping("/DistribuirVianda")
    public ResponseEntity<String> procesarSolicitudDistribucion(@RequestBody DistribucionDeViandaDTO distribucionDTO) {
        System.out.println(distribucionDTO.getMotivoDeDistribucion() + ' ' + distribucionDTO.getCantidadDeViandas() + ' ' + distribucionDTO.getHeladeraDeOrigenID() + ' ' + distribucionDTO.getHeladeraDestinoID());

        DistribucionDeViandas nuevaDistribucion = procesarDTO(distribucionDTO);

        System.out.println(nuevaDistribucion.getMotivoDeDistribucion().toString() + ' ' + nuevaDistribucion.getCantidadDeViandasAMover() + ' ' + nuevaDistribucion.getHeladeraDeOrigen().getUbicacion().getNombreCompletoDeUbicacion() + ' ' + nuevaDistribucion.getHeladeraDestino().getUbicacion().getNombreCompletoDeUbicacion());

        try{
            GestorDePermisosDeApertura.generarPermisosDeDistribucion(nuevaDistribucion);
            return ResponseEntity.ok("La declaración de la distribución se ha realizado con éxito!");
        }catch (ExcepcionViandasInsuficientesEnOrigen e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad de viandas en la heladera origen es insuficiente.");
        }
        catch (ExcepcionNoHayEspacioEnDestino e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay espacio suficiente en la heladera destino.");
        }
    }

    private DistribucionDeViandas procesarDTO(DistribucionDeViandaDTO dto){
        Heladera heladeraOrigenElegida = RepositorioHeladeras.getInstancia().buscarHeladeraPorId(dto.getHeladeraDeOrigenID());
        Heladera heladeraDestinoElegida = RepositorioHeladeras.getInstancia().buscarHeladeraPorId(dto.getHeladeraDestinoID());

        // TO DO: Para cuando implemente Persistencia y Sesión.
        // Es headcodeo puro lo otro, con el fin de probar que funcione el resto
        /*
        Optional<Heladera> heladeraOrigenElegida = repositorioHeladeras.findById(dto.getHeladeraDeOrigenID());
        Optional<Heladera> heladeraDestinoElegida = repositorioHeladeras.findById(dto.getHeladeraDestinoID());
        DistribucionDeViandas nuevaDistribucion = DistribucionDeViandasMapper.crearDistribucionAPartirDe(dto,
                                                                                                         heladeraOrigenElegida.get(),
                                                                                                         heladeraDestinoElegida.get(),
                                                                                                       gestorInicioDeSesion.obtenerColaboradorPorID());
                                                                                                       */
        AccesoDeColaborador accesoDeColaborador = new AccesoDeColaborador("1010", colaborador);
        colaborador.setTarjeta(accesoDeColaborador);

        DistribucionDeViandas nuevaDistribucion = DistribucionDeViandasMapper.crearDistribucionAPartirDe(dto,
                heladeraOrigenElegida,
                heladeraDestinoElegida,
                colaborador);

        return nuevaDistribucion;
    }

}
