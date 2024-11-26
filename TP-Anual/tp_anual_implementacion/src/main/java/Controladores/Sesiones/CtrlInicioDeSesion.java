package Controladores.Sesiones;

import Modelo.Dominio.Usuario;
import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> iniciarSesion(@RequestBody Usuario usuario) {
        try {
            int idColaborador = gestorInicioDeSesion.buscarUsuarioEnBD(usuario.getUsuario(), usuario.getContrasenia());

            return ResponseEntity.ok("Usuario y contraseña validados exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Usuario y contrasenia incorrectos. Vuelva a intentarlo");
        }
    }
}