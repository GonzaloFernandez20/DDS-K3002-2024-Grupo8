package Modelo.seguridad;

import Repositories.colaborador.ColaboradorRepository;
import Repositories.UsuariosRepository;
import Modelo.Dominio.Usuario;
import Modelo.Dominio.colaborador.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestorInicioDeSesion {

    private final UsuariosRepository usuariosRepository;
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public GestorInicioDeSesion(UsuariosRepository usuariosRepository, ColaboradorRepository colaboradorRepository) {
        this.usuariosRepository = usuariosRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public Optional<Usuario> obtenerUsuarioEnBD(String usuario, String contrasenia) {
        return usuariosRepository.buscarUsuario(usuario, contrasenia);
    }

    public Colaborador obtenerColaboradorPorID(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String idUsuario = authentication.getName();
            return colaboradorRepository.obtenerColaboradorSegunID(idUsuario);
        }
        return null;
    }

}