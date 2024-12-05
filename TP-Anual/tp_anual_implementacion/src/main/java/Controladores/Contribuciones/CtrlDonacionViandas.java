package Controladores.Contribuciones;

import DTOs.DonacionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.Mappers.DonacionDeViandasMapper;
import Modelo.seguridad.GestorInicioDeSesion;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CtrlDonacionViandas {

    private final HeladeraRepository repositorioHeladeras;
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlDonacionViandas(HeladeraRepository repositorioHeladeras, GestorInicioDeSesion gestorInicioDeSesion) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
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

    @GetMapping("/DonarViandas")
    public String mostrarHeladeras(Model model) {
        if(Objects.isNull(gestorInicioDeSesion.obtenerColaboradorPorID().getTarjeta())) {
            return "PedirTarjetaColaborador";
        }
        setEstados();
        model.addAttribute("heladeras", heladeras);
        model.addAttribute("estados", estados);
        return "DonarViandas";
    }

    private DonacionDeViandas procesarDTO(DonacionDeViandaDTO dto){
        Optional<Heladera> heladeraElegida = repositorioHeladeras.findById(dto.getHeladeraID());
        if (heladeraElegida.isPresent()) {
            return DonacionDeViandasMapper.crearDonacionDeViandasAPartirDe(dto, heladeraElegida.get(), colaborador);
        } else {
            throw new RuntimeException("Heladera no encontrada con ID: " + dto.getHeladeraID());
        }
    }

    @PostMapping("/DonarViandas")
    public  ResponseEntity<String> donarVianda(@RequestBody DonacionDeViandaDTO donacionDTO){
        if (donacionDTO == null || donacionDTO.getViandasDTO() == null || donacionDTO.getViandasDTO().isEmpty()) {
            throw new RuntimeException("La donación o la lista de viandas está vacía");
        }

        try {
            DonacionDeViandas nuevaDonacion = procesarDTO(donacionDTO);
            GestorDePermisosDeApertura.generarPermisoDeDonación(nuevaDonacion);
            return ResponseEntity.ok("Donacion realizada con éxito!");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

}
