package Modelo.seguridad;

import Modelo.Excepciones.ExcepcionLongitudDeContrasenia;

public class CumpleConElLargo extends Criterio{
    @Override
    public boolean criterioSeguridad(String contrasenia) throws Exception {
        if (contrasenia.length() <= 8) {
            throw new RuntimeException("La contraseña es demasiado corta. Debe tener más de 8 caracteres.");
        } else if (contrasenia.length() >= 64) {
            throw new ExcepcionLongitudDeContrasenia("La contraseña es demasiado larga. No debe tener más de 64 caracteres.");
        }
        return true;
    }
}
