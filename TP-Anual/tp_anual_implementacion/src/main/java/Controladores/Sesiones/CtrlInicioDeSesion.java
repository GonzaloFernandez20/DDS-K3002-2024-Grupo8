package Controladores.Sesiones;

import Modelo.seguridad.SesionActiva.Usuario;
import Modelo.seguridad.GestorInicioDeSesion;
import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import Modelo.seguridad.SesionActiva.UtilsJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/InicioDeSesion")
public class CtrlInicioDeSesion {

    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlInicioDeSesion(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    @GetMapping
    public String inicioDeSesion() {
        return "InicioDeSesion";
    }

    @PostMapping
    public ResponseEntity<Void> iniciarSesion(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioObtenido = gestorInicioDeSesion.obtenerUsuarioEnBD(usuario.getUsuario(), usuario.getContrasenia());

        if (usuarioObtenido.isPresent()){
            String token = UtilsJWT.generarToken(usuarioObtenido.get().getUsuario());
            ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        }else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Devolvemos solo el codigo de error, sin mensaje
    }
}