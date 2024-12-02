package Modelo.seguridad;

import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.UsuariosRepository;
import Modelo.seguridad.SesionActiva.Usuario;
import Modelo.Dominio.colaborador.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Colaborador obtenerColaboradorPorID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String credenciales = authentication.getName();
            String[] partes = credenciales.split(" ");
            String nombreDeUsuario = partes[0];
            String contrasenia = partes[1];

            Optional<Usuario> usuario = usuariosRepository.buscarUsuario(nombreDeUsuario, contrasenia);
            if (usuario.isPresent()) {
                return usuario.get().getColaborador();
            }
        } // De momento sirve dejarlo de esta manera. En un futuro podriamos directamente traer el id_colaborador
        return null;
    }

}