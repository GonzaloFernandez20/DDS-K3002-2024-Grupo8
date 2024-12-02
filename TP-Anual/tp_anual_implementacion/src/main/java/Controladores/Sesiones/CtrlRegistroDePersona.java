package Controladores.Sesiones;

import DTOs.ColaboradorHumanoDTO;
import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.Repositories.UsuariosRepository;
import Modelo.Mappers.ColabHumanoMapper;
import Modelo.Mappers.ColabJuridicoMapper;
import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import Modelo.seguridad.SesionActiva.Usuario;
import Modelo.seguridad.SesionActiva.UtilsJWT;
import Modelo.seguridad.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CtrlRegistroDePersona {

    private final UsuariosRepository usuariosRepository;

    @Autowired
    public CtrlRegistroDePersona(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @PostMapping("/ValidarUsuario")
    public ResponseEntity<String> validarUsuario(@RequestParam String contrasenia) {
        try {
            Validador.getInstancia().validarConstrasenia(contrasenia);
            return ResponseEntity.ok("Usuario y contraseña validados exitosamente.");
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
        Usuario usuario = ColabJuridicoMapper.crearColaboradorJuridicoAPartirDe(colaboradorDTO);
        usuariosRepository.save(usuario);   //Cargar colaborador en BD

        String token = UtilsJWT.generarToken(usuario.getUsuario()+" "+usuario.getContrasenia());
        ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/RegistrarColaboradorHumano")
    public ResponseEntity<String> registrarColaboradorHumano(@RequestBody ColaboradorHumanoDTO colaboradorDTO) {
        Usuario usuario = ColabHumanoMapper.crearColaboradorHumanoAPartirDe(colaboradorDTO);
        usuariosRepository.save(usuario);   //Cargar colaborador en BD

        String token = UtilsJWT.generarToken(usuario.getUsuario()+" "+usuario.getContrasenia());
        ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
