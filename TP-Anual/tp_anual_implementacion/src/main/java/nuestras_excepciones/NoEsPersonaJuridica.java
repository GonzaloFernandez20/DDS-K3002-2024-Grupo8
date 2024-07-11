package nuestras_excepciones;

public class NoEsPersonaJuridica extends Exception{
    public NoEsPersonaJuridica(String message) {
        super(message);
    }
}
