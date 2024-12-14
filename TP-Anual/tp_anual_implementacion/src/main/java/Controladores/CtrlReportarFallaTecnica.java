package Controladores;

import DTOs.*;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.DonacionDeViandasRepository;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.incidentes.FallaTecnicaRepository;
import Modelo.Dominio.Repositories.reportes.ReporteDeFallasRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.reportes.ReporteDeFallas;
import Modelo.Mappers.DonacionDeViandasMapper;
import Modelo.Mappers.FactoryFallaTecnica;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;
import Utils.DescargaDeArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class CtrlReportarFallaTecnica {
    private final ColaboradorRepository colaboradorRepository;
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final HeladeraRepository heladeraRepository;
    private final FallaTecnicaRepository fallaTecnicaRepository;

    @Autowired
    public CtrlReportarFallaTecnica(ColaboradorRepository colaboradorRepository, GestorInicioDeSesion gestorInicioDeSesion,
                                    HeladeraRepository heladeraRepository, FallaTecnicaRepository fallaTecnicaRepository) {
        this.colaboradorRepository = colaboradorRepository;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.heladeraRepository = heladeraRepository;
        this.fallaTecnicaRepository = fallaTecnicaRepository;
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
    public ResponseEntity<String> donarVianda(@RequestBody FallaTecnicaDTO fallaTecnicaDTO){
        if (fallaTecnicaDTO == null || fallaTecnicaDTO.getHeladera() == null) {
            throw new RuntimeException("Debes completar todo el reporte!");
        }

        try {
            DescargaDeArchivo.guardarArchivo("/fotosHeladerasReportadas/", fallaTecnicaDTO.getFoto());
            Optional<Heladera> heladeraElegida = heladeraRepository.findById(fallaTecnicaDTO.getHeladera().getIdHeladera());
            if (heladeraElegida.isPresent()) {
                FallaTecnica fallaTecnica = GestorDeIncidentes.reportarFallaTecnica(fallaTecnicaDTO);
                fallaTecnicaRepository.save(fallaTecnica);
            } else {
                throw new RuntimeException("Heladera no encontrada con ID: " + fallaTecnicaDTO.getHeladera().getIdHeladera());
            }
            return ResponseEntity.ok("Reporte realizado con éxito!");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }
    /*private String guardarFoto(MultipartFile fotoFalla) {

        String filePath = "/fotosHeladerasReportadas/" + fotoFalla.getOriginalFilename();
        try {
            fotoFalla.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }*/
}