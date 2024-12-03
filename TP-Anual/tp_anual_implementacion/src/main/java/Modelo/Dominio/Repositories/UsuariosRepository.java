package Modelo.Dominio.Repositories;

import Modelo.seguridad.SesionActiva.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

    // Ejecutamos la Query usando SQL NATIVO
    @Query(value = "SELECT * FROM usuario WHERE usuario = ?1 AND contrasenia = ?2", nativeQuery = true)
    Optional<Usuario> buscarUsuario(String usuario, String contrasenia);

    @Query(value = "SELECT * FROM usuario WHERE usuario = ?1", nativeQuery = true)
    Optional<Usuario> findByNombreDeUsuario(String nombreDeUsuario);
}
