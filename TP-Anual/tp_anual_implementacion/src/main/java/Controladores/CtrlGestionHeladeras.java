package Controladores;

import DTOs.HeladeraDTO;
import DTOs.AlertaDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Mappers.AlertaMapper;
import Modelo.Mappers.BuilderHeladera;
import Modelo.Mappers.HeladeraMapper;
import Modelo.seguridad.GestorInicioDeSesion;

import Repositories.heladera.HeladeraRepository;
import Repositories.incidentes.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CtrlGestionHeladeras {
    private final HeladeraRepository heladeraRepository;
    private final AlertaRepository alertaRepository;
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlGestionHeladeras(HeladeraRepository repositorioHeladeras, AlertaRepository alertaRepository, GestorInicioDeSesion gestorInicioDeSesion) {
        this.heladeraRepository = repositorioHeladeras;
        this.alertaRepository = alertaRepository;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    @GetMapping("/ModificarColaboradorJuridicoHeladeras")
    public String mostrarHeladerasYAlertas(Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        List<HeladeraDTO> heladeras = HeladeraMapper.convertirListaDeHeladerasEnDTO(heladeraRepository.traerHeladerasDeUnColaborador(colaborador.getId_colaborador()));
        List<HeladeraDTO> heladerasConAlertas = HeladeraMapper.convertirListaDeHeladerasEnDTO(heladeraRepository.traerHeladerasDeUnColaboradorConAlertas(colaborador.getId_colaborador()));
        List<HeladeraDTO> heladerasDadasDeBaja = HeladeraMapper.convertirListaDeHeladerasEnDTO(heladeraRepository.traerHeladerasDadasDeBajaDeUnColaborador(colaborador.getId_colaborador()));

        List<AlertaDTO> alertas = alertaRepository.traerAlertasDeUnColaborador(colaborador.getId_colaborador()).stream().
                map(alerta -> AlertaMapper.convertirEnAlertaDTO(alerta)).collect(Collectors.toList());

        model.addAttribute("warnings", alertas);
        model.addAttribute("heladeras", heladeras);
        model.addAttribute("heladerasConAlertas", heladerasConAlertas);
        model.addAttribute("heladerasDadasDeBaja", heladerasDadasDeBaja);

        return "ModificarColaboradorJuridicoHeladeras";
    }

    @PostMapping("/ModificarHeladera")
    public ResponseEntity<String> modificarHeladera(@RequestBody HeladeraDTO heladeraAModificarDTO) {
        try{
            Heladera heladeraAnterior = heladeraRepository.findById(heladeraAModificarDTO.getIdHeladera()).get();

            Heladera heladeraAModificar = BuilderHeladera.actualizarHeladeraAPartirDe(heladeraAnterior, heladeraAModificarDTO);

            heladeraRepository.save(heladeraAModificar);

            return ResponseEntity.ok("La heladera ha sido modificada satisfactoriamente.");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/EliminarHeladera")
    public ResponseEntity<String> eliminarHeladera(@RequestBody Integer idHeladera) {
        try {
            Heladera heladera = heladeraRepository.findById(idHeladera).get();

            heladera.setEstado(EstadoHeladera.DADA_DE_BAJA);
            heladeraRepository.save(heladera);

            return ResponseEntity.ok("La heladera fue dada de baja exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
