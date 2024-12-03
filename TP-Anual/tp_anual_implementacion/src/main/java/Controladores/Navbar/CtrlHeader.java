package Controladores.Navbar;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlHeader {
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlHeader(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    @GetMapping("/Header")
    public String mostrarNavBar(Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        if (colaborador == null) {
            model.addAttribute("sesion", "NoIniciada");
        } else {
            model.addAttribute("sesion", "Iniciada");
        }
        return "Header";
    }
}
