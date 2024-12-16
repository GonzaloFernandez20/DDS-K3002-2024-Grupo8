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
import Modelo.seguridad.SesionActiva.UtilsJWT;
import Repositories.incidentes.FallaTecnicaRepository;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;
import Utils.DescargaDeArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
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
    private final Repositories.incidentes.FallaTecnicaRepository fallaTecnicaRepository;

    @Autowired
    public CtrlReportarFallaTecnica(ColaboradorRepository colaboradorRepository, GestorInicioDeSesion gestorInicioDeSesion,
                                    HeladeraRepository heladeraRepository, Repositories.incidentes.FallaTecnicaRepository fallaTecnicaRepository) {
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
    public ResponseEntity<String> reportarFallaTecnica(
            @RequestParam("heladera") String heladera, @RequestParam("descripcionFalla") String descripcionFalla, @RequestParam("fotoFalla") MultipartFile fotoFalla,
            @CookieValue("token") String token) {

        System.out.println("Token recibido: " + token);

        try {
            // Decodificar el token para obtener la información del colaborador
            String id_colaborador = UtilsJWT.obtenerSujetoDelToken(token);

            // Asignar colaborador al DTO
            System.out.println("ID del colaborador decodificado: " + id_colaborador);
            Colaborador colaborador = colaboradorRepository.obtenerColaboradorSegunID(id_colaborador);

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
    /*
    @PostMapping(value = "/ReportarFallaTecnica", consumes = "multipart/form-data")
    public ResponseEntity<String> reportarFallaTecnica(
            @RequestParam("heladera") String heladeraId,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fotoFalla") MultipartFile fotoFalla,
            @CookieValue("token") String token) {
        System.out.println("Inicio del método reportarFallaTecnica");
        System.out.println("Token recibido: " + token);

        try {
            if (fotoFalla.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de foto está vacío.");
            }

            // Guardar la foto en una ubicación persistente
            String rutaDirectorio = "C:/fotosHeladerasReportadas/";
            String nombreArchivo = fotoFalla.getOriginalFilename();
            String rutaCompleta = rutaDirectorio + nombreArchivo;

            // Crear directorio si no existe
            Files.createDirectories(Paths.get(rutaDirectorio));

            // Guardar archivo en disco
            fotoFalla.transferTo(Paths.get(rutaCompleta));

            // Proseguir con el procesamiento
            System.out.println("Archivo guardado en: " + rutaCompleta);

            if (rutaCompleta.length() > 255) {
                throw new RuntimeException("La ruta del archivo supera el límite permitido de 255 caracteres.");
            }

            // Decodificar el token para obtener la información del colaborador
            String id_colaborador = UtilsJWT.obtenerSujetoDelToken(token);

            // Buscar colaborador
            Colaborador colaborador = colaboradorRepository.obtenerColaboradorSegunID(id_colaborador);

            // Verificar heladera
            Optional<Heladera> heladeraElegida = heladeraRepository.findById(Integer.parseInt(heladeraId));
            if (!heladeraElegida.isPresent()) {
                throw new RuntimeException("Heladera no encontrada con ID: " + heladeraId);
            }

            // Crear el DTO manualmente (porque no usamos @RequestBody aquí)
            FallaTecnicaDTO fallaTecnicaDTO = new FallaTecnicaDTO(colaborador, descripcion, heladeraElegida.get(), fotoFalla);
            //fallaTecnicaDTO.setDescripcion(descripcion);
            //fallaTecnicaDTO.setColaboradorInformante(colaborador);
            //fallaTecnicaDTO.setFoto(fotoFalla);
            //fallaTecnicaDTO.setHeladera(heladeraElegida.get().getIdHeladera());

            // Guardar la foto
            //DescargaDeArchivo.guardarArchivo("/fotosHeladerasReportadas/", fotoFalla);

            // Reportar falla
            FallaTecnica fallaTecnica = GestorDeIncidentes.reportarFallaTecnica(fallaTecnicaDTO);
            fallaTecnicaRepository.save(fallaTecnica);

            System.out.println("Se realizó el reporte completo");
            return ResponseEntity.ok("Reporte realizado con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }*/


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
