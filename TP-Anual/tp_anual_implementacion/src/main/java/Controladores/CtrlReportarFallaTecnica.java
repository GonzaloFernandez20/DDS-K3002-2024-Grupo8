package Controladores;

import DTOs.FallaTecnicaDTO;
import DTOs.HeladeraDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Repositorios.RepositorioHeladeras;
import Utils.DescargaDeArchivo;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class CtrlReportarFallaTecnica {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Fabian", "Bielinski", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "40.303.456", Sexo.MASCULINO), new Direccion("Montes Carballo", "1689")), List.of(new WhatsApp("15 1610-6160")));

    // TODO: Reemplazar por el repositorio real
    private final List<HeladeraDTO> heladerasAReportar = null/*RepositorioHeladeras.getInstancia().getHeladeras().stream().map(this::convertirHeladeraADTO).collect(Collectors.toList())*/;

    @GetMapping("/ReportarFallaTecnica")
    public String mostrarFormulario(Model model) {
        model.addAttribute("heladerasAReportar", heladerasAReportar);

        return "ReportarFallaTecnica";
    }

    @PostMapping("/ReportarFallaTecnica")
    public String procesarFormulario(@RequestParam(value = "heladeraReportada", defaultValue = "0") String idHeladeraReportada,
                                     @RequestParam(value = "descripcionFalla", defaultValue = "0") String descripcionFalla,
                                     @RequestParam(value = "fotoFalla") MultipartFile fotoFalla,
                                     Model model) {

        // TODO: Reemplazar por el repositorio real
        //Heladera heladeraReportada = RepositorioHeladeras.getInstancia().buscarHeladeraPorId(Integer.parseInt(idHeladeraReportada));
        Heladera heladeraReportada = null;

        String pathFotoFalla = null;
        if (!fotoFalla.isEmpty()) {
            pathFotoFalla = DescargaDeArchivo.guardarArchivo("/fotosHeladerasReportadas/", fotoFalla);
        }



        FallaTecnicaDTO fallaTecnicaDTO = new FallaTecnicaDTO(colaborador, heladeraReportada, descripcionFalla, pathFotoFalla);
        GestorDeIncidentes.reportarFallaTecnica(fallaTecnicaDTO);

        model.addAttribute("mensaje", "Falla reportada exitosamente!");

        return mostrarFormulario(model);
    }

    private HeladeraDTO convertirHeladeraADTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getid_heladera(), heladera.getColaboradorACargo(), heladera.getCapacidadDeViandas(), heladera.getModelo().getNombreModelo(), heladera.getModelo().getTemperaturaMaxima(), heladera.getModelo().getTemperaturaMinima(), heladera.getUbicacion().getDireccion().getCalle(), heladera.getUbicacion().getDireccion().getAltura(), heladera.getUbicacion().getCiudad(), heladera.getUbicacion().getNombreDelPunto(), heladera.getPuestaEnFuncionamiento(), heladera.getLatitud(), heladera.getLongitud());
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