package Controladores;

import DTOs.DonacionDeViandaDTO;
import DTOs.ViandaDTO;
import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeViandas;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona_vulnerable.EstadoDeVivienda;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;
import Modelo.Factorys.BuilderDonacionDeViandas;
import Repositorios.RepositorioHeladeras;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CtrlDarDeAltaPersonaVulnerable {
    /*List<EstadoDeVivienda> estadoDeViviendas = new ArrayList<>();

    public void estadoDeViviendas() {
        if(estadoDeViviendas.isEmpty()) {
            estadoDeViviendas.add(EstadoDeVivienda.SITUACION_DE_CALLE);
            estadoDeViviendas.add(EstadoDeVivienda.POSEE_DOMICILIO);
        }
    }

    List<TipoDeDocumento> tipoDeDocumentos = new ArrayList<>();

    public void tipoDeDocumentos() {
        if(tipoDeDocumentos.isEmpty()) {
            tipoDeDocumentos.add(TipoDeDocumento.CI);
            tipoDeDocumentos.add(TipoDeDocumento.LC);
            tipoDeDocumentos.add(TipoDeDocumento.DNI);
            tipoDeDocumentos.add(TipoDeDocumento.LE);
            tipoDeDocumentos.add(TipoDeDocumento.PASAPORTE);
        }
    }
    PersonaHumana personaHumana = new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234"));
    private final Colaborador colaborador = new Colaborador(personaHumana, List.of(new WhatsApp("15 2350-2350")));


    @GetMapping("/DarDeAltaPersonaEnSitVulnerable")
    public String mostrarHeladeras(Model model) {
        estadoDeViviendas();
        tipoDeDocumentos();
        model.addAttribute("estados", estadoDeViviendas);
        model.addAttribute("tiposDocumento", tipoDeDocumentos);
        return "DarDeAltaPersonaEnSitVulnerable";
    }

    @PostMapping("/DarDeAltaPersonaEnSitVulnerable")
    public String recibirDatos (@RequestParam(value = "nombrePersonaVul", defaultValue = "0") String nombrePersonaVul,
                                @RequestParam(value = "fechaNacimientoPersonaVul", defaultValue = "0") LocalDate fechaNacimientoPersonaVul,
                                @RequestParam(value = "sitViviendaPersonaVul", defaultValue = "0") EstadoDeVivienda sitViviendaPersonaVul,
                                @RequestParam(value = "domicilioPersonaVul", defaultValue = "0") String domicilioPersonaVul,
                                @RequestParam(value = "tipoDocPersonaVul", defaultValue = "0") TipoDeDocumento tipoDocPersonaVul,
                                @RequestParam(value = "numeroDocPersonaVul", defaultValue = "0") String numeroDocPersonaVul,
                                @RequestParam(value = "tieneMenoresPersonaVul", defaultValue = "0") String tieneMenoresPersonaVul,
                                @RequestParam(value = "cantidadMenoresPersonaVul", defaultValue = "0") String cantidadMenoresPersonaVul,
                                @RequestParam(value = "tarjetaPersonaVul", defaultValue = "0") String tarjetaPersonaVul,
                                Model model){
        Vinculacion vinculacion = new Vinculacion(tarjetaPersonaVul, )
        PersonaSituacionVulnerable personaSituacionVulnerable = new PersonaSituacionVulnerable(sitViviendaPersonaVul, cantidadMenoresPersonaVul, new Vinculacion(tarjetaPersonaVul, self, colaborador), personaHumana);
        if(colaborador.getTarjeta().aperturaAutorizada(heladeraElegida)) {
            DonacionDeViandaDTO donacionDeViandaDTO = new DonacionDeViandaDTO(colaborador, heladeraElegida, viandas);

            BuilderDonacionDeViandas.crearDonacionAPartirDe(donacionDeViandaDTO);
            GestorDonacionDeViandas.crearContribucion(donacionDeViandaDTO);
            model.addAttribute("mensaje", "Donacion realizada con éxito!");
            return mostrarHeladeras(model);
        } else{
            model.addAttribute("mensaje", "No tiene acceso a la heladera!");
            return "Home";
        }

    }*/
}
