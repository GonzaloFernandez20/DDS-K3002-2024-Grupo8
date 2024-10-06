package Controllers;

import DTOs.HeladeraDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.suscripcion.GestorDeSuscripciones;
import Repositorios.RepositorioHeladeras;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CtrlSuscripciones {

    // DEBE USAR DTOs
    private final List<Heladera> heladeras = RepositorioHeladeras.getInstancia().getHeladeras();

    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), null, null), null);
    //

    @GetMapping("/ModificarColaboradorHumanoSuscripciones")
    public String mostrarHeladeras(Model model) {
        model.addAttribute("heladeras", heladeras.stream().map(heladera -> heladera.getUbicacion().getNombreDelPunto()));
        return "ModificarColaboradorHumanoSuscripciones";
    }

    @PostMapping("/ColaboradorSuscrito")
    public String recibirSeleccion(@RequestParam("optionsHeladera") String stringHeladeraElegida,
                                   @RequestParam("check-suscripcion-disponen-viandas") boolean checkSuscripcionDisponenViandas, @RequestParam("cantidadDeViandasDisponibles") int cantidadDeViandasDisponibles,
                                   @RequestParam("check-suscripcion-faltan-viandas") boolean checkSuscripcionFaltanViandas, @RequestParam("cantidadDeViandasFaltantes") int cantidadDeViandasFaltantes,
                                   @RequestParam("check-suscripcion-desperfecto") boolean checkSuscripcionDesperfecto) {

        Heladera heladeraElegida = null;
        /* = heladeras.stream().filter(heladera -> heladera.getUbicacion().getNombreDelPunto().equals(stringHeladeraElegida)).findFirst();*/

        // TO DO: CHECKEAR QUE CUANDO EL CHECKBOX ESTÉ EN TRUE HAYA ALGO EN EL INPUT

        String suscripcionesValidas = "";

        if(validarSuscripcion(checkSuscripcionDisponenViandas, heladeraElegida, "Notificar cuando disponga únicamente de " + cantidadDeViandasDisponibles + " viandas.")) {
            suscripcionesValidas += "Notificar cuando disponga únicamente de" + cantidadDeViandasDisponibles + " viandas. ";
        }

        if(validarSuscripcion(checkSuscripcionFaltanViandas, heladeraElegida, "Notificar cuando falten " + cantidadDeViandasFaltantes + " viandas para que se llene la heladera.")) {
            suscripcionesValidas += "Notificar cuando falten " + cantidadDeViandasFaltantes + " viandas para que se llene la heladera. ";
        }

        if(validarSuscripcion(checkSuscripcionDesperfecto, heladeraElegida, "Notificar cuando se produjo una falla.")) {
            suscripcionesValidas += "Notificar cuando se produjo una falla.";
        }

        if(suscripcionesValidas.isEmpty()) {
            return "Sus suscripciones fallaron.";
        }

        return "Logró suscribirse a: ";
    }

    private boolean validarSuscripcion(boolean checkbox, Heladera heladera, String evento) {
        if(checkbox) {
            GestorDeSuscripciones.getInstancia().registrarSuscripcion(heladera, colaborador, evento);
            /* En realidad todo depende de si el gestor logra registrar la suscripción, debería
                            devolver un boolean para mostrar el OK que está en el DS.
            */
            return true;
        }
        return false;
    }
}
