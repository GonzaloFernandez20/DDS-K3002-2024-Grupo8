package Controladores;

import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;


import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CtrlModificarColaborador {

    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlModificarColaborador(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

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
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        model.addAttribute("tipoColaborador", tipoPersonaDelColaborador());
        model.addAttribute("puntos", colaborador.getPuntosAcumulados());

        return "ModificarColaboradorPremios";
    }

    private String tipoPersonaDelColaborador() {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        if(colaborador.getPersona() instanceof PersonaHumana) {
            return "PersonaHumana";
        }

        if(colaborador.getPersona() instanceof PersonaJuridica) {
            return "PersonaJuridica";
        }

        return "";
    }
}