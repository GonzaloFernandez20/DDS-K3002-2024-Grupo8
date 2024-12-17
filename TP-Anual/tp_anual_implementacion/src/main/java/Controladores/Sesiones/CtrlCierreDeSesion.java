package Controladores.Sesiones;

import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlCierreDeSesion {
    @GetMapping("/CierreDeSesion")
    public ResponseEntity<Void> cerrarSesion(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie cookie = GeneradorDeCookie.eliminarCookie();
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        return ResponseEntity
                .status(302)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.LOCATION, "/Home")
                .build();
    }
}
