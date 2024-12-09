package Controladores;

import DTOs.HeladeraDTO;
import DTOs.AlertaDTO;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.incidentes.AlertaRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Mappers.AlertaMapper;
import Modelo.Mappers.BuilderHeladera;
import Modelo.Mappers.HeladeraMapper;
import Modelo.seguridad.GestorInicioDeSesion;

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

    private List<HeladeraDTO> heladeras;
    private List<AlertaDTO> alertas;

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

        heladeras = heladeraRepository.traerHeladerasDeUnColaborador(colaborador.getId_colaborador()).stream().
                map(heladera -> HeladeraMapper.convertirEnHeladeraDTO(heladera)).collect(Collectors.toList());
        alertas = alertaRepository.traerAlertasDeUnColaborador(colaborador.getId_colaborador()).stream().
                map(alerta -> AlertaMapper.convertirEnAlertaDTO(alerta)).collect(Collectors.toList());
        List<HeladeraDTO> heladerasConAlertas = heladeraRepository.traerHeladerasDeUnColaboradorConAlertas(colaborador.getId_colaborador()).stream().
                map(heladera -> HeladeraMapper.convertirEnHeladeraDTO(heladera)).collect(Collectors.toList());

        model.addAttribute("warnings", alertas);
        model.addAttribute("heladeras", heladeras);
        model.addAttribute("heladerasConAlertas", heladerasConAlertas);

        return "ModificarColaboradorJuridicoHeladeras";
    }

    @PostMapping("/ModificarHeladera")
    public ResponseEntity<String> modificarHeladera(@RequestBody HeladeraDTO heladeraAModificarDTO) {
        Heladera heladeraAnterior = heladeraRepository.findById(heladeraAModificarDTO.getIdHeladera()).get();

        Heladera heladeraAModificar = BuilderHeladera.actualizarHeladeraAPartirDe(heladeraAnterior, heladeraAModificarDTO);

        heladeraRepository.save(heladeraAModificar);

        return ResponseEntity.ok("La heladera ha sido modificada satisfactoriamente.");
    }

    @PostMapping("/EliminarHeladera")
    public ResponseEntity<String> eliminarHeladera(@RequestBody Integer idHeladera) {
        heladeraRepository.eliminarDependenciasDeLaHeladera(idHeladera);
        heladeraRepository.eliminarHeladeraPorId(idHeladera);

        return ResponseEntity.ok("La heladera fue dada de baja exitosamente.");
    }
}
