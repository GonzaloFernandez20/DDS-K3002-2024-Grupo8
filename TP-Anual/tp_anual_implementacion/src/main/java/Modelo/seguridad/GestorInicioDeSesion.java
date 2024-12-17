package Modelo.seguridad;

import Repositories.UsuariosRepository;
import Modelo.seguridad.SesionActiva.Usuario;
import Modelo.Dominio.colaborador.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestorInicioDeSesion {

    private final UsuariosRepository usuariosRepository;

    @Autowired
    public GestorInicioDeSesion(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public Optional<Usuario> obtenerUsuarioEnBD(String usuario, String contrasenia) {
        return usuariosRepository.buscarUsuario(usuario, contrasenia);
    }

    public Optional<Usuario> obtenerUsuarioSegunMail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    public Colaborador obtenerColaboradorPorID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<Usuario> usuario;
            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
                OAuth2User oauth2User = oauth2Token.getPrincipal();
                String email = oauth2User.getAttribute("email"); // Obtener el email
                usuario = usuariosRepository.findByEmail(email);
            }else{
                String nombreDeUsuario = authentication.getName();
                usuario = usuariosRepository.findByNombreDeUsuario(nombreDeUsuario);
            }
            if (usuario.isPresent()) {
                return usuario.get().getColaborador();
            }
        }
        return null;
    }

    public Usuario obtenerUsuarioDeSesion(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<Usuario> usuario;
            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
                OAuth2User oauth2User = oauth2Token.getPrincipal();
                String email = oauth2User.getAttribute("email"); // Obtener el email
                usuario = usuariosRepository.findByEmail(email);
            }else{
                String nombreDeUsuario = authentication.getName();
                usuario = usuariosRepository.findByNombreDeUsuario(nombreDeUsuario);
            }
            if (usuario.isPresent()) {
                return usuario.get();
            }
        }
        return null;
    }

}