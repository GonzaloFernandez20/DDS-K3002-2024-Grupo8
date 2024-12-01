package Controladores.Navbar;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;

import static Modelo.Dominio.Persona.TipoOrganizacion.ONG;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class CtrlSeleccionDeContribucion {
    //COLABORADORES HARDCODEADOS
    //private final Colaborador colaboradorActual = new Colaborador(new PersonaHumana("Fabian", "Bielinski", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "40.303.456", Sexo.MASCULINO), new Direccion("Montes Carballo", "1689", "1407")), List.of(new WhatsApp("15 1610-6160")));
    private final Colaborador colaboradorActual = new Colaborador(new PersonaJuridica("Pinos S.A.", ONG, "Cerrajeria", new Direccion("Oliden", "779", "1408")), List.of(new WhatsApp("15 4419-6172")));

    @GetMapping("/Colaborar")
    public String mostrarColaboraciones(Model model) {
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