package Modelo.seguridad;

import Modelo.Dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class GestorInicioDeSesion {
    @Autowired
    private DataSource dataSource; // Inyección del DataSource
    public static int buscarUsuarioEnBD (String usuario, String contrasenia) throws RuntimeException {
/*
        TODO: Gestionar la request a base de datos

        String sqlQuery = "SELECT usuario, contrasenia FROM usuario WHERE usuario = ? && contrasenia = ?";

        Connection conexionBD = dataSource.getConnection();
        PreparedStatement preparedStatement = conexionBD.prepareStatement(sqlQuery);
        // Establecer el valor del parámetro
        preparedStatement.setString(1, usuario);
        preparedStatement.setString(2, contrasenia);


        // Ejecutar la consulta
        ResultSet resultado = preparedStatement.executeQuery();
        if (resultado.next()) {
            // Procesar los resultados
            String usuarioObtenido = resultado.getString("usuario");
            String contraseniaObtenida = resultado.getString("contrasenia");

            if (Objects.equals(usuarioObtenido, usuario.getUsuario()) &&
                    Objects.equals(contraseniaObtenida, usuario.getContrasenia())) {
                return 0;
            }
        }
        */

        // DEVUELVE EL ID DEL COLABORADOR
        return 0;
    }
}

