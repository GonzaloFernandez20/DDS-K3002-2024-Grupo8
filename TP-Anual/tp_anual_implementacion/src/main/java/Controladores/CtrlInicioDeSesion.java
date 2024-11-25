package Controladores;

import Modelo.Dominio.Usuario;
import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;
import java.sql.SQLException;

@Controller
@RequestMapping("/IniciarSesion")
public class CtrlInicioDeSesion {

    @GetMapping
    public String inicioDeSesion() {
        return "InicioDeSesion";
    }

    @PostMapping
    public ResponseEntity<String> iniciarSesion(@RequestParam String usuario,
                                                @RequestParam String contrasenia) {
        try {
            int idColaborador = GestorInicioDeSesion.buscarUsuarioEnBD(usuario, contrasenia);

            return ResponseEntity.ok("Usuario y contraseña validados exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Usuario y contrasenia incorrectos. Vuelva a intentarlo");
        }
    }
}