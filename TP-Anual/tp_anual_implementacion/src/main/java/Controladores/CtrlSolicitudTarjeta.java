package Controladores;

import Modelo.Dominio.Accesos_a_heladeras.GestorDeAperturasAHeladeras;

import Modelo.Dominio.Accesos_a_heladeras.GestorTarjetas;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CtrlSolicitudTarjeta {
    GestorInicioDeSesion gestorInicioDeSesion;
    GestorTarjetas gestorTarjetas;

    @Autowired
    public CtrlSolicitudTarjeta(GestorInicioDeSesion gestorInicioDeSesion,
                                GestorTarjetas gestorTarjetas) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorTarjetas = gestorTarjetas;
    }


    @GetMapping("/PedirTarjetaColaborador")
    public String pedirAcceso() {
        return "PedirTarjetaColaborador";
    }

    @GetMapping("/EntregarTarjetasDeAcceso")
    public String entregarAcceso() { return "EntregarTarjetasDeAcceso"; }

    @PostMapping("/SolicitarTarjetas")
    public ResponseEntity<String> solicitarTarjetas(@RequestBody Integer cantidadTarjetas,
                                                    RedirectAttributes redirectAttributes) {
        gestorTarjetas.generarSolicitud(gestorInicioDeSesion.obtenerColaboradorPorID(), cantidadTarjetas);
        String mensaje = "Vas a recibir las tarjetas en los próximos días.";
        if(cantidadTarjetas == 1) {
            mensaje = "Vas a recibir la tarjeta en los próximos días.";
        }
        redirectAttributes.addFlashAttribute("mensaje", mensaje);

        return ResponseEntity.ok().body(mensaje);
    }

    @PostMapping("/IngresarTarjetaColaborador")
    public ResponseEntity<String> ingresarTarjetaColaborador(@RequestBody String codigoTarjeta) {
        try {
            Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
            gestorTarjetas.registrarAccesoDeColaborador(codigoTarjeta, colaborador);
            return ResponseEntity.ok("El código de tarjeta fue ingresado correctamente");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
