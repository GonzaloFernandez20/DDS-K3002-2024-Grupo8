package Controladores;

import Modelo.Dominio.Accesos_a_heladeras.GestorDeAccesosAHeladeras;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CtrlPedirTarjetaColaborador {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200")), List.of(new WhatsApp("15 2350-2350")));

    @GetMapping("/PedirTarjetaColaborador")
    public String pedirAcceso() {
        return "PedirTarjetaColaborador";
    }

    @PostMapping("/PedirTarjetaColaborador")
    public String confirmarPedidoDeAcceso(@RequestParam("respuestaFormAcceso") String rtaAcceso,
                                          RedirectAttributes redirectAttributes) {

        if(rtaAcceso.equals("pedirTarjeta")) {
            // esta hardcodeado, habria que ver como generar una sesion. Cuando hagamos esto hay que modificar el diagrama de secuencia.
            Direccion direccion = new Direccion("Cordoba", "123");
            Documento documento = new Documento(TipoDeDocumento.DNI, "123456", Sexo.FEMENINO);
            PersonaHumana persona = new PersonaHumana("Carla", "Gonzalez", LocalDate.now(), documento, direccion);
            GestorDeAccesosAHeladeras.getInstancia().generarSolicitud(persona, 1);
            redirectAttributes.addFlashAttribute("mensaje", "Vas a recibir la tarjeta en los próximos días.");
        }

        return "redirect:/Home";
    }
}
