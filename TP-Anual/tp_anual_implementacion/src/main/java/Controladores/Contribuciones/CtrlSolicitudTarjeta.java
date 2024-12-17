package Controladores.Contribuciones;

import Modelo.Dominio.Accesos_a_heladeras.GestorTarjetas;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.seguridad.GestorInicioDeSesion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class CtrlSolicitudTarjeta {
    //Dependencias ----------------------------------------------------------------------------------------------------
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorTarjetas gestorTarjetas;

    @Autowired
    public CtrlSolicitudTarjeta(GestorInicioDeSesion gestorInicioDeSesion,
                                GestorTarjetas gestorTarjetas) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorTarjetas = gestorTarjetas;
    }

    //GET MAPPING --------------------------------------------------------------------------------------------------
    @GetMapping("/PedirTarjetaColaborador")
    public String pedirAcceso() {
        return "PedirTarjetaColaborador";
    }

    @GetMapping("/EntregarTarjetasDeAcceso")
    public String entregarAcceso() { return "EntregarTarjetasDeAcceso"; }

    //POST MAPPING ---------------------------------------------------------------------------------------------------
    @PostMapping("/SolicitarTarjetas")
    public ResponseEntity<String> solicitarTarjetas(@RequestBody Integer cantidadTarjetas) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
        gestorTarjetas.generarSolicitud(colaborador, cantidadTarjetas);

        String mensaje;
        if(cantidadTarjetas == 1) {
            mensaje = "Recibirás la tarjeta en los próximos días.";
            log.info("El colaborador ID:{} solicitó una tarjeta para acceder a las heladeras", colaborador.getId_colaborador());
        }
        else
        {
            mensaje = "Recibirás las tarjetas en los próximos días.";
            log.info("El colaborador ID:{} solicitó {} tarjetas para repartir a personas en situación vulnerable", colaborador.getId_colaborador(), cantidadTarjetas);
        }
        return ResponseEntity.ok().body(mensaje);
    }

    @PostMapping("/IngresarTarjetaColaborador")
    public ResponseEntity<String> ingresarTarjetaColaborador(@RequestBody String codigoTarjeta) {
        try {
            Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
            gestorTarjetas.registrarAccesoDeColaborador(codigoTarjeta, colaborador);
            return ResponseEntity.ok("Tu tarjeta fue registrada con éxito! Ya tenés acceso a las heladeras");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
