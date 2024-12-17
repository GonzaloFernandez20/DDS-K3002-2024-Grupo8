package Controladores.Contribuciones;

import DTOs.VinculacionPersonaVulnerableDTO;

import Modelo.Dominio.Accesos_a_heladeras.GestorTarjetas;
import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.Persona_vulnerable.EstadoDeVivienda;
import Modelo.Mappers.VinculacionPersonaVulnerableMapper;
import Modelo.seguridad.GestorInicioDeSesion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CtrlRegistrarPersonaVulnerable {

    //DEPENDENCIAS -----------------------------------------------------------------------------------------------------
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorTarjetas gestorTarjetas;

    @Autowired
    public CtrlRegistrarPersonaVulnerable(GestorInicioDeSesion gestorInicioDeSesion, GestorTarjetas gestorTarjetas) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorTarjetas = gestorTarjetas;

    }

    // GET MAPPING ----------------------------------------------------------------------------------------------------
    @GetMapping("/DarDeAltaPersonaEnSitVulnerable")
    public String mostrarDatos(Model model) {
        Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();
        if (colaboradorActual.getPersona() instanceof PersonaJuridica) {
            return "PedirRegistroHumano";
        }

        estadoDeViviendas();
        tipoDeDocumentos();
        sexo();
        model.addAttribute("estados", estadoDeViviendas);
        model.addAttribute("tiposDocumento", tipoDeDocumentos);
        model.addAttribute("sexo", sexo);

        return "DarDeAltaPersonaEnSitVulnerable";
    }
    private final List<EstadoDeVivienda> estadoDeViviendas = new ArrayList<>();
    private final List<TipoDeDocumento> tipoDeDocumentos = new ArrayList<>();
    private final List<Sexo> sexo = new ArrayList<>();

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

    // POST MAPPING --------------------------------------------------------------------------------------------------------------
    @PostMapping("/DarDeAltaPersonaEnSitVulnerable")
    public ResponseEntity<String> registrarPersonaVulnerable (@RequestBody VinculacionPersonaVulnerableDTO personaVulnerableDTO) {
        Vinculacion nuevoVulnerablevinculado = procesarDTO(personaVulnerableDTO);
        try {
            gestorTarjetas.registrarVinculacion(nuevoVulnerablevinculado);
            return ResponseEntity.ok().body("Registro realizado con éxito!");
        }catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Vinculacion procesarDTO(VinculacionPersonaVulnerableDTO dto) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
        return VinculacionPersonaVulnerableMapper.crearVinculacionAPartirDeDTO(dto,colaborador);
    }
}
