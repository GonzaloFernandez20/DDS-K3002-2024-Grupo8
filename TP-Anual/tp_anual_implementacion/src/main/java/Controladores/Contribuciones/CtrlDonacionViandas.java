package Controladores.Contribuciones;

import DTOs.DonacionDeViandaDTO;
import DTOs.HeladeraDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.Mappers.DonacionDeViandasMapper;
import Repositorios.RepositorioHeladeras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CtrlDonacionViandas {

    private final HeladeraRepository repositorioHeladeras;
    @Autowired
    public CtrlDonacionViandas(HeladeraRepository repositorioHeladeras) {
        this.repositorioHeladeras = repositorioHeladeras;
    }

    //TODO 1: traerse las heladeras de la bd y mapearlas en HeladeraSeleccionDTO usando el mapper
    private final List<HeladeraSeleccionDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().
            map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());
    /*heladeraRepository.findAll().stream().map(heladera->newHeladeraDTO(heladera.getId(),heladera.getNombre())).collect(Collectors.toList());*/

    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200")), List.of(new WhatsApp("15 2350-2350")));
    List<EstadoVianda> estados = new ArrayList<>();


    public void setEstados() {
        if(estados.isEmpty()) {
            estados.add(EstadoVianda.NO_ENTREGADA);
            estados.add(EstadoVianda.ENTREGADA);
            estados.add(EstadoVianda.EN_TRASLADO);
            estados.add(EstadoVianda.VENCIDA);
            estados.add(EstadoVianda.RETIRADA);
        }
    }

    Heladera heladeraSeleccionada;

    @GetMapping("/PrevioDonarViandas")
    public String mostrarHeladeras(Model model) {
        List<Heladera> heladerasList = RepositorioHeladeras.getInstancia().getHeladeras();
        System.out.println("Heladeras desde el repositorio: " + heladerasList.size());

        model.addAttribute("heladeras", heladeras);
        return "PrevioDonarViandas";
    }

    @PostMapping("/PrevioDonarViandas")
    public ResponseEntity<String> procesarSolicitudDonacionViadas(@RequestBody int IDHeladera){
        Optional<Heladera> heladera= this.repositorioHeladeras.findById(IDHeladera);
        /*Direccion direccion = new Direccion(heladeraDTO.getCalle(), heladeraDTO.getAltura());
        Ubicacion ubicacion = new Ubicacion(direccion, heladeraDTO.getCiudad(), heladeraDTO.getNombreDelPunto());
        Modelo modelo = new Modelo(heladeraDTO.getTempMAXmodelo(), heladeraDTO.getTempMINmodelo());*/
        heladeraSeleccionada = heladera.get();
        /*
        * try {
            gestorTarjetas.registrarVinculacion(nuevoVulnerablevinculado);
            return ResponseEntity.ok("Registro realizado con éxito!");
        }catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }*/
        return ResponseEntity.ok("Heladera ingresada con éxito!");
    }

    private DonacionDeViandas procesarDonacionDTO(DonacionDeViandaDTO dto){
        DonacionDeViandas nuevaDonacion = DonacionDeViandasMapper.crearDonacionDeViandasAPartirDe(dto, heladeraSeleccionada,colaborador);
        return nuevaDonacion;
    }

    @GetMapping("/DonarViandas")
    public String solicitarDatosViandas(Model model) {
        //if(Objects.isNull(colaborador.getTarjeta())) {
        //  return "PedirTarjetaColaborador";
        //}
        setEstados();
        model.addAttribute("estados", estados);
        return "DonarViandas";
    }

    @PostMapping("/DonarViandas")
    public String donarVianda(@RequestBody DonacionDeViandaDTO donacionDTO, Model model){

        DonacionDeViandas nuevaDonacion = procesarDonacionDTO(donacionDTO);
        GestorDePermisosDeApertura.generarPermisoDeDonación(nuevaDonacion);
        model.addAttribute("mensaje", "Donacion realizada con éxito!");
        return "Home";

    }
}
