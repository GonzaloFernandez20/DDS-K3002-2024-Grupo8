package seguridad;

public class CumpleConElLargo extends Criterio{
    @Override
    public boolean aplicarCriterioSeguridad(String contrasenia) {
        return contrasenia.length() > 8 && contrasenia.length() < 64;
    }
}
