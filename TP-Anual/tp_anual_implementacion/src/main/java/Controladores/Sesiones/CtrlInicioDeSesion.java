package Controladores.Sesiones;

import Modelo.Dominio.Usuario;
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
            String token = UtilsJWT.generarToken(usuarioObtenido.get().getId_colaborador().toString());
            ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        }else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Devolvemos solo el codigo de error, sin mensaje
    }
}


/*
    @PostMapping
    public ResponseEntity<String> iniciarSesion(@RequestBody Usuario usuario) {

        if (gestorInicioDeSesion.obtenerUsuarioEnBD(usuario.getUsuario(), usuario.getContrasenia())){
            String token = UtilsJWT.generarToken(usuario.getUsuario());
            // ResponseEntity.ok("Usuario y contraseña validados exitosamente.");
            return ResponseEntity.ok(token);
        }else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario y contrasenia incorrectos. Vuelva a intentarlo");
        try {
            gestorInicioDeSesion.obtenerUsuarioEnBD(usuario.getUsuario(), usuario.getContrasenia());
            return ResponseEntity.ok("Usuario y contraseña validados exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario y contrasenia incorrectos. Vuelva a intentarlo");
        }
    }




    */