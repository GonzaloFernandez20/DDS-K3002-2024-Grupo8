package Controladores;

import DTOs.ColaboradorHumanoDTO;
import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Mappers.BuilderColabHumano;
import Modelo.Mappers.BuilderColabJuridico;
import Modelo.seguridad.Validador;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@Controller
public class CtrlRegistroDePersona {

    @PostMapping("/ValidarUsuario")
    public ResponseEntity<String> validarUsuario(@RequestBody Map<String, String> request) { // Investigar
        String nombreDeUsuario = request.get("nombreDeUsuario");
        String contrasenia = request.get("contrasenia");

        try {
            Validador.getInstancia().validarConstrasenia(contrasenia);
            Validador.getInstancia().validarNombreDeUsuario(nombreDeUsuario);

            return ResponseEntity.ok("Usuario y contraseña validados exitosamente."); // Si sale to bien
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ------------------------------------

    @GetMapping("/Registro")
    public String registro (){ return "RegistroPersona"; }

    // ------------------------------------
    // SEPARAMOS EN REGISTROS DE COLABORADORES JURIDICOS Y HUMANOS
    @PostMapping("/RegistrarColaboradorJuridico")
    public ResponseEntity<String> registrarColaboradorJuridico(@RequestBody ColaboradorJuridicoDTO colaboradorDTO) { // Investigar
        Colaborador colaborador = BuilderColabJuridico.crearColaboradorJuridicoAPartirDe(colaboradorDTO);
        //Registrar usuario en el sistema
        //Cargar colaborador en BD
        return ResponseEntity.ok("Usuario creado con exito.");
    }

    @PostMapping("/RegistrarColaboradorHumano")
    public ResponseEntity<String> registrarColaboradorHumano(@RequestBody ColaboradorHumanoDTO colaboradorDTO) {
        Colaborador colaborador = BuilderColabHumano.crearColaboradorHumanoAPartirDe(colaboradorDTO);
        //Registrar usuario en el sistema
        //Cargar colaborador en BD
        return ResponseEntity.ok("Usuario creado con exito.");
    }
}
