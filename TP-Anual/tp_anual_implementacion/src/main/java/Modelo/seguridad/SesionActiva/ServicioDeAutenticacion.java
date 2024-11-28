package Modelo.seguridad.SesionActiva;

import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioDeAutenticacion {
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public ServicioDeAutenticacion(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    public String loggearUsuario(String nombreDeUsuario, String contrasenia) throws RuntimeException{
        if(gestorInicioDeSesion.existeUsuarioEnBD(nombreDeUsuario, contrasenia)){
            return UtilsJWT.generarToken(nombreDeUsuario); // Token Generado
        } else return null;
    }

    public static boolean esAutentico(String token) {
        return UtilsJWT.validarToken(token);
    }
}
