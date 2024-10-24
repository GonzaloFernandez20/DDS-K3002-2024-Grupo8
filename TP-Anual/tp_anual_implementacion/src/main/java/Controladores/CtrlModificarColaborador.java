package Controladores;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;


import Modelo.Dominio.persona.TipoOrganizacion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CtrlModificarColaborador {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    // Humano:
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    // Juridico:
    // private final Colaborador colaborador = new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com")));
    //

    @GetMapping("/ModificarColaborador")
    public String mostrarColaborador() {
        switch (tipoPersonaDelColaborador()) {
            case "PersonaHumana":
                return "ModificarColaboradorHumano";
            case "PersonaJuridica":
                return "ModificarColaboradorJuridicoCuenta";
            default:
                return "Home";
        }
    }

    @GetMapping("/ModificarColaboradorPremios")
    public String mostrarPremios(Model model) {
        model.addAttribute("tipoColaborador", tipoPersonaDelColaborador());
        model.addAttribute("puntos", colaborador.getPuntosAcumulados());

        return "ModificarColaboradorPremios";
    }

    private String tipoPersonaDelColaborador() {
        if(colaborador.getPersona() instanceof PersonaHumana) {
            return "PersonaHumana";
        }

        if(colaborador.getPersona() instanceof PersonaJuridica) {
            return "PersonaJuridica";
        }

        return "";
    }
}
