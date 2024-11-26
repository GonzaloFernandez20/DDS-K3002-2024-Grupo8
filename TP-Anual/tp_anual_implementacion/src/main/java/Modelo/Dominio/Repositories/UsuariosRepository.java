package Modelo.Dominio.Repositories;

import Modelo.Dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

    // Ejecutamos la Query usando SQL NATIVO
    @Query(value = "SELECT * FROM usuario WHERE usuario = ?1 AND contrasenia = ?2", nativeQuery = true)
    Usuario buscarUsuario(String usuario, String contrasenia);


}
