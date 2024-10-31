package Controladores;

import DTOs.ColaboradorHumanoDTO;
import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Factorys.BuilderColabHumano;
import Modelo.Factorys.BuilderColabJuridico;
import Modelo.seguridad.Validador;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class CtrlSesiones {

    // ------------------------------------
    @GetMapping("/InicioDeSesion")
    public String inicioDeSesion(){ return "InicioDeSesion"; }

    @GetMapping("/Registro")
    public String registro (){ return "RegistroPersona"; }

    // ------------------------------------
    @PostMapping("/IniciarSesion")
    public ResponseEntity<String> iniciarSesion(@RequestParam("usuario") String usuario,
                                                @RequestParam("contrasenia") String contrasenia)
    {
        // TODO: HACER UNA REQUEST A BD PARA TRAER ESE USUARIO
        return null;
    }
    // ------------------------------------
    // SEPARAMOS EN REGISTROS DE COLABORADORES JURIDICOS Y HUMANOS
    @PostMapping("/RegistrarColaboradorJuridico")
    public ResponseEntity<String> registrarColaboradorJuridico(ColaboradorJuridicoDTO colaboradorDTO) {
        Colaborador colaborador = BuilderColabJuridico.crearColaboradorJuridicoAPartirDe(colaboradorDTO);
        //Registrar usuario en el sistema
        //Cargar colaborador en BD
        return ResponseEntity.ok("Usuario creado con exito.");
    }

    @PostMapping("/RegistrarColaboradorHumano")
    public ResponseEntity<String> registrarColaboradorHumano(ColaboradorHumanoDTO colaboradorDTO) {
        Colaborador colaborador = BuilderColabHumano.crearColaboradorHumanoAPartirDe(colaboradorDTO);
        //Registrar usuario en el sistema
        //Cargar colaborador en BD
        return ResponseEntity.ok("Usuario creado con exito.");
    }
    // ------------------------------------

    @PostMapping("/ValidarUsuario")
    public ResponseEntity<String> validarUsuario(@RequestBody Map<String, String> request) {
        String nombreDeUsuario = request.get("nombreDeUsuario");
        String contrasenia = request.get("contrasenia");

        // TODO: Crear un metodo en el validador para validar que el nombre de usuario no esta duplicado en el sistema
        // TODO: Manejar las excepciones para devolver el error correcto ante el fallo de cada criterio
        try {
            Validador.getInstancia().validarConstrasenia(contrasenia);
            //Validador.getInstancia().validarNombreDeUsuario(nombreDeUsuario);

            return ResponseEntity.ok("Usuario y contraseña validados exitosamente."); // Si sale to bien
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
