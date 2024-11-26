package Modelo.seguridad;

import Modelo.Dominio.Repositories.UsuariosRepository;
import Modelo.Dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorInicioDeSesion {

    private final UsuariosRepository usuariosRepository;

    @Autowired
    public GestorInicioDeSesion(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public int buscarUsuarioEnBD(String usuario, String contrasenia) throws RuntimeException {

        Usuario usuarioObtenido = usuariosRepository.buscarUsuario(usuario, contrasenia);

        if (usuarioObtenido == null) {
            throw new RuntimeException("No existe ese usuario "+usuario+" "+contrasenia);
        }

        return usuarioObtenido.getId_usuario();
    }

}

