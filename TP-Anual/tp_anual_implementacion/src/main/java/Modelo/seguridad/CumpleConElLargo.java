package Modelo.seguridad;

public class CumpleConElLargo extends Criterio{
    @Override
    public boolean criterioSeguridad(String contrasenia) {
        return contrasenia.length() > 8 && contrasenia.length() < 64;
    }
}
