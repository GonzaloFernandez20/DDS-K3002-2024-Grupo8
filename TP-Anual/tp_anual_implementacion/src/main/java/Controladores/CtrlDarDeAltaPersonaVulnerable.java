package Controladores;

import Modelo.Dominio.Accesos_a_heladeras.GestorDeAccesosAHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.SolicitudTarjeta;
import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona_vulnerable.EstadoDeVivienda;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CtrlDarDeAltaPersonaVulnerable {
    PersonaHumana personaHumana = new PersonaHumana("Luis", "Gomez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200"));
    private final Colaborador colaborador = new Colaborador(personaHumana, List.of(new WhatsApp("15 2350-2350")));

    List<EstadoDeVivienda> estadoDeViviendas = new ArrayList<>();
    List<TipoDeDocumento> tipoDeDocumentos = new ArrayList<>();

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
    // hardcodeo solicitud de tarjeta para tener el codigo de tarjeta de antemano
    SolicitudTarjeta solicitudTarjeta = new SolicitudTarjeta(personaHumana, 1);

    @GetMapping("/DarDeAltaPersonaEnSitVulnerable")
    public String mostrarDatos(Model model) {
        estadoDeViviendas();
        tipoDeDocumentos();
        model.addAttribute("estados", estadoDeViviendas);
        model.addAttribute("tiposDocumento", tipoDeDocumentos);

        return "DarDeAltaPersonaEnSitVulnerable";
    }

    @PostMapping("/DarDeAltaPersonaEnSitVulnerable")
    public String recibirDatos (@RequestParam(value = "nombrePersonaVul", defaultValue = "0") String nombrePersonaVul,
                                @RequestParam(value = "apellidoPersonaVul", defaultValue = "0") String apellidoPersonaVul,
                                @RequestParam(value = "fechaNacimientoPersonaVul", defaultValue = "0") String stringFechaNacimientoPersonaVul,
                                @RequestParam(value = "sitViviendaPersonaVul", defaultValue = "0") String stringSitViviendaPersonaVul,
                                @RequestParam(value = "calleDomicilioPersonaVul", defaultValue = "0") String calleDomicilioPersonaVul,
                                @RequestParam(value = "alturaDomicilioPersonaVul", defaultValue = "0") String alturaDomicilioPersonaVul,
                                @RequestParam(value = "tipoDocPersonaVul", required = false) String stringTipoDocPersonaVul,
                                @RequestParam(value = "numeroDocPersonaVul", required = false) String numeroDocPersonaVul,
                                @RequestParam(value = "tieneMenoresPersonaVul", required = false) String tieneMenoresPersonaVul,
                                @RequestParam(value = "cantidadMenoresPersonaVul", defaultValue = "0") int cantidadMenoresPersonaVul,
                                @RequestParam(value = "tarjetaPersonaVul", defaultValue = "0") String tarjetaPersonaVul, Model model)
    {
        TipoDeDocumento tipoDocPersonaVul;
        if(Objects.isNull(stringTipoDocPersonaVul)) {
            tipoDocPersonaVul = null;
        } else {
            tipoDocPersonaVul = TipoDeDocumento.valueOf(stringTipoDocPersonaVul);
        }

        EstadoDeVivienda sitViviendaPersonaVul;
        if(Objects.isNull(stringSitViviendaPersonaVul)) {
            sitViviendaPersonaVul = null;
        } else {
            sitViviendaPersonaVul = EstadoDeVivienda.valueOf(stringSitViviendaPersonaVul);
        }

        LocalDate fechaNacimientoPersonaVul = LocalDate.parse(stringFechaNacimientoPersonaVul);
        Documento documento = new Documento(tipoDocPersonaVul, numeroDocPersonaVul, null);
        Direccion direccion = new Direccion(calleDomicilioPersonaVul, alturaDomicilioPersonaVul);
        PersonaHumana personaHumanaEnSitVulnerable = new PersonaHumana(nombrePersonaVul, apellidoPersonaVul, fechaNacimientoPersonaVul, documento, direccion);
        PersonaSituacionVulnerable personaSituacionVulnerable = new PersonaSituacionVulnerable(sitViviendaPersonaVul, cantidadMenoresPersonaVul, null, personaHumanaEnSitVulnerable);
        Vinculacion vinculacion = new Vinculacion(tarjetaPersonaVul, personaSituacionVulnerable, colaborador);
        personaSituacionVulnerable.setVinculacion(vinculacion);
        model.addAttribute("mensaje", "Vinculación realizada con éxito!");
        return "Home";
    }
}
