package Controladores.Navbar;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;

import static Modelo.Dominio.Persona.TipoOrganizacion.ONG;

import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class CtrlSeleccionDeContribucion {
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlSeleccionDeContribucion(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    @GetMapping("/Colaborar")
    public String mostrarColaboraciones(Model model) {
        Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();

        if (colaboradorActual.getPersona() instanceof PersonaHumana) {
            model.addAttribute("tipoColaborador", "PersonaHumana");
        } else if (colaboradorActual.getPersona() instanceof PersonaJuridica) {
            model.addAttribute("tipoColaborador", "PersonaJuridica");
        }
        model.addAttribute("colaborador", colaboradorActual);
        return "Colaborar";
    }

    @PostMapping("/Colaborar")
    public String redireccionarColaboracion(@RequestParam("value") String tipoDeContribucion, RedirectAttributes redirectAttributes) {

        System.out.println("Tipo de contribución recibido: " + tipoDeContribucion);

        try {
            redirectAttributes.addFlashAttribute("mensaje", "Puede colaborar en " + tipoDeContribucion);
            return "redirect:/" + tipoDeContribucion;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "No puede colaborar en " + tipoDeContribucion);
            return "redirect:/error";
        }
    }

}