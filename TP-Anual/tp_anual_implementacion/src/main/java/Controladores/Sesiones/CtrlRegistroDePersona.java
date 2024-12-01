package Controladores.Sesiones;

import DTOs.ColaboradorHumanoDTO;
import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Mappers.BuilderColabHumano;
import Modelo.Mappers.BuilderColabJuridico;
import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import Modelo.seguridad.SesionActiva.UtilsJWT;
import Modelo.seguridad.Validador;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@Controller
public class CtrlRegistroDePersona {

    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public CtrlRegistroDePersona(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

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

    @GetMapping("/RegistroPersona")
    public String registro (){ return "RegistroPersona"; }

    // ------------------------------------
    // SEPARAMOS EN REGISTROS DE COLABORADORES JURIDICOS Y HUMANOS
    @PostMapping("/RegistrarColaboradorJuridico")
    public ResponseEntity<String> registrarColaboradorJuridico(@RequestBody ColaboradorJuridicoDTO colaboradorDTO) { // Investigar
        Colaborador colaborador = BuilderColabJuridico.crearColaboradorJuridicoAPartirDe(colaboradorDTO);
        colaboradorRepository.save(colaborador);    //Cargar colaborador en BD

        String token = UtilsJWT.generarToken(colaborador.getId_colaborador().toString());
        ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/RegistrarColaboradorHumano")
    public ResponseEntity<String> registrarColaboradorHumano(@RequestBody ColaboradorHumanoDTO colaboradorDTO) {
        Colaborador colaborador = BuilderColabHumano.crearColaboradorHumanoAPartirDe(colaboradorDTO);
        colaboradorRepository.save(colaborador);

        String token = UtilsJWT.generarToken(colaborador.getId_colaborador().toString());
        ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
