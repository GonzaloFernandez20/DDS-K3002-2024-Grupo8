package Controladores;

import DTOs.*;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Modelo.seguridad.SesionActiva.UtilsJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class CtrlReportarFallaTecnica {
    private final Repositories.colaborador.ColaboradorRepository colaboradorRepository;
    private final Repositories.heladera.HeladeraRepository heladeraRepository;
    private final Repositories.incidentes.FallaTecnicaRepository fallaTecnicaRepository;
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlReportarFallaTecnica(Repositories.colaborador.ColaboradorRepository colaboradorRepository,
                                    Repositories.heladera.HeladeraRepository heladeraRepository, Repositories.incidentes.FallaTecnicaRepository fallaTecnicaRepository, GestorInicioDeSesion gestorInicioDeSesion) {
        this.colaboradorRepository = colaboradorRepository;
        this.heladeraRepository = heladeraRepository;
        this.fallaTecnicaRepository = fallaTecnicaRepository;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    private List<HeladeraSeleccionDTO> heladeras;


    @GetMapping("/ReportarFallaTecnica")
    public String mostrarHeladeras(Model model) {
        heladeras = heladeraRepository.traerHeladerasActivasEnElSistema().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());
        model.addAttribute("heladeras", heladeras);
        return "ReportarFallaTecnica";
    }

    @PostMapping("/ReportarFallaTecnica")
    public ResponseEntity<String> reportarFallaTecnica(@RequestParam("heladera") String heladera,
                                                       @RequestParam("descripcionFalla") String descripcionFalla,
                                                       @RequestParam("fotoFalla") MultipartFile fotoFalla) {

        try {
            // Decodificar el token para obtener la información del colaborador
            //String id_colaborador = UtilsJWT.obtenerSujetoDelToken(token);

            // Asignar colaborador al DTO
            //System.out.println("ID del colaborador decodificado: " + id_colaborador);
            //Colaborador colaborador = colaboradorRepository.obtenerColaboradorSegunID(id_colaborador);

            Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
            // Verificar la heladera
            Optional<Heladera> heladeraElegida = heladeraRepository.findById(Integer.parseInt(heladera));
            System.out.println("Heladera encontrada: " + heladeraElegida.isPresent());
            if (heladeraElegida.isPresent()) {
                FallaTecnicaDTO fallaTecnicaDTO = new FallaTecnicaDTO(colaborador, descripcionFalla, heladeraElegida.get(), fotoFalla);
                System.out.println("Llamando a GestorDeIncidentes.reportarFallaTecnica");
                FallaTecnica fallaTecnica = GestorDeIncidentes.reportarFallaTecnica(fallaTecnicaDTO);
                System.out.println("Falla técnica reportada: " + fallaTecnica);
                System.out.println("Guardando en fallaTecnicaRepository");
                fallaTecnicaRepository.save(fallaTecnica);
                System.out.println("Guardado exitosamente en fallaTecnicaRepository");

                System.out.println("Se realizó el reporte completo");
            } else {
                throw new RuntimeException("Heladera no encontrada con ID: " + heladera);
            }
            return ResponseEntity.ok("Reporte realizado con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }
}