package Controladores.Sesiones;

import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlCierreDeSesion {
    @GetMapping("/CierreDeSesion")
    public ResponseEntity<Void> cerrarSesion() {
        ResponseCookie cookie = GeneradorDeCookie.eliminarCookie();
        return ResponseEntity
                .status(302)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.LOCATION, "/Home")
                .build();
    }
}
