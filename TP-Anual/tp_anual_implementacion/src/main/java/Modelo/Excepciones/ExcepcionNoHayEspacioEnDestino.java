package Modelo.Excepciones;

public class ExcepcionNoHayEspacioEnDestino extends RuntimeException {
    public ExcepcionNoHayEspacioEnDestino(String message) {
        super(message);
    }
}