package Controladores.Contribuciones;

import DTOs.VinculacionPersonaVulnerableDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorTarjetas;
import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.Repositories.Accesos_a_heladeras.VinculacionRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona_vulnerable.EstadoDeVivienda;
import Modelo.Dominio.Persona_vulnerable.PersonaSituacionVulnerable;

import Modelo.Mappers.VinculacionPersonaVulnerableMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CtrlRegistrarPersonaVulnerable {

    private GestorInicioDeSesion gestorInicioDeSesion;
    private GestorTarjetas gestorTarjetas;

    private List<EstadoDeVivienda> estadoDeViviendas = new ArrayList<>();
    private List<TipoDeDocumento> tipoDeDocumentos = new ArrayList<>();
    private List<Sexo> sexo = new ArrayList<>();

    @Autowired
    public CtrlRegistrarPersonaVulnerable(GestorInicioDeSesion gestorInicioDeSesion,
                                          GestorTarjetas gestorTarjetas) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorTarjetas = gestorTarjetas;
    }

    public void estadoDeViviendas() {
        if(estadoDeViviendas.isEmpty()) {
            estadoDeViviendas.add(EstadoDeVivienda.SITUACION_DE_CALLE);
            estadoDeViviendas.add(EstadoDeVivienda.POSEE_DOMICILIO);
        }
    }

    public void tipoDeDocumentos() {
        if (tipoDeDocumentos.isEmpty()) {
            tipoDeDocumentos.add(TipoDeDocumento.CI);
            tipoDeDocumentos.add(TipoDeDocumento.LC);
            tipoDeDocumentos.add(TipoDeDocumento.DNI);
            tipoDeDocumentos.add(TipoDeDocumento.LE);
            tipoDeDocumentos.add(TipoDeDocumento.PASAPORTE);
        }
    }

    public void sexo() {
        if (sexo.isEmpty()) {
            sexo.add(Sexo.MASCULINO);
            sexo.add(Sexo.FEMENINO);
            sexo.add(Sexo.OTRO);
        }
    }

    @GetMapping("/DarDeAltaPersonaEnSitVulnerable")
    public String mostrarDatos(Model model) {
        estadoDeViviendas();
        tipoDeDocumentos();
        sexo();
        model.addAttribute("estados", estadoDeViviendas);
        model.addAttribute("tiposDocumento", tipoDeDocumentos);
        model.addAttribute("sexo", sexo);

        return "DarDeAltaPersonaEnSitVulnerable";
    }

    @PostMapping("/DarDeAltaPersonaEnSitVulnerable")
    public ResponseEntity<String> resgistrarPersonaVulnerable (@RequestBody VinculacionPersonaVulnerableDTO personaVulnerableDTO) {
        Vinculacion nuevoVulnerablevinculado = procesarDTO(personaVulnerableDTO);
        try {
            gestorTarjetas.registrarVinculacion(nuevoVulnerablevinculado);
            return ResponseEntity.ok("Registro realizado con éxito!");
        }catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Vinculacion procesarDTO(VinculacionPersonaVulnerableDTO dto) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
        return VinculacionPersonaVulnerableMapper.crearVinculacionAPartirDeDTO(dto,colaborador);
    }
}
