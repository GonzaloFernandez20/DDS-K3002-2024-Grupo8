package Controladores.Contribuciones;

import DTOs.DonacionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.AccesoDeColaboradorRepository;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.AperturaConPermisoRepository;
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

    private final AccesoDeColaboradorRepository accesoDeColaboradorRepository;
    private final AperturaConPermisoRepository aperturaConPermisoRepository;
    private final HeladeraRepository heladeraRepository;

    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorDePermisosDeApertura gestorDePermisosDeApertura;

    private List<EstadoVianda> estados;

    @Autowired
    public CtrlDonacionViandas( GestorInicioDeSesion gestorInicioDeSesion,
                               AccesoDeColaboradorRepository accesoDeColaboradorRepository,
                               AperturaConPermisoRepository aperturaConPermisoRepository, HeladeraRepository heladeraRepository, GestorDePermisosDeApertura gestorDePermisosDeApertura) {
        this.accesoDeColaboradorRepository = accesoDeColaboradorRepository;
        this.aperturaConPermisoRepository = aperturaConPermisoRepository;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.heladeraRepository = heladeraRepository;
        this.gestorDePermisosDeApertura = gestorDePermisosDeApertura;
        this.estados = new ArrayList<>();
    }

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

        List<HeladeraSeleccionDTO> heladeras = heladeraRepository.findAll().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());

        if(Objects.isNull(gestorInicioDeSesion.obtenerColaboradorPorID().getTarjeta())) {
            return "PedirTarjetaColaborador";
        }
        setEstados();
        model.addAttribute("heladeras", heladeras);
        model.addAttribute("estados", estados);
        return "DonarViandas";
    }

    @PostMapping("/DonarViandas")
    public  ResponseEntity<String> donarVianda(@RequestBody DonacionDeViandaDTO donacionDTO){
        if (donacionDTO == null || donacionDTO.getViandasDTO() == null || donacionDTO.getViandasDTO().isEmpty()) {
            throw new RuntimeException("La donación o la lista de viandas está vacía");
        }

        try {
            DonacionDeViandas nuevaDonacion = procesarDTO(donacionDTO);
            gestorDePermisosDeApertura.generarPermisoDeDonación(nuevaDonacion);
            return ResponseEntity.ok("Donacion realizada con éxito!");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    private DonacionDeViandas procesarDTO(DonacionDeViandaDTO dto){
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        Optional<Heladera> heladeraElegida = heladeraRepository.findById(dto.getHeladeraID());
        if (heladeraElegida.isPresent()) {
            return DonacionDeViandasMapper.crearDonacionDeViandasAPartirDe(dto, heladeraElegida.get(), colaborador);
        } else {
            throw new RuntimeException("Heladera no encontrada con ID: " + dto.getHeladeraID());
        }
    }
}
