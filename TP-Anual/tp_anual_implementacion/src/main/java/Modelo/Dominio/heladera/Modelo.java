package Modelo.Dominio.heladera;

public class Modelo {
    private String nombreModelo;
    float temperaturaMaxima;
    float temperaturaMinima;

    public Modelo(float temperaturaMaxima, float temperaturaMinima) {
        // Si del front solo se ingresa el nombre del modelo, de alguna forma habria que obtener a partir de ese
        // nombre cual es la temperatura max y min. Una solucion seria una MockApi que dado un modelo devuelva su
        // min y max, y entonces se haga el llamado a esta API dentro del constructor para instanciar el modelo.
        // la alternativa mas sencilla es que le pida al usuario en el form los datos de temp min y max sin necesidad
        // de pasar el nombre de modelo.
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
    }

    public boolean controlarTemperatura(double temperatura){
        return temperatura >= temperaturaMinima && temperatura <= temperaturaMaxima;
    }

    // ---- Getters y Setters
    public float getTemperaturaMaxima() {
        return temperaturaMaxima;
    }
    public float getTemperaturaMinima() { return temperaturaMinima; }
    public void setTemperaturaMaxima(float temperaturaMaxima) { this.temperaturaMaxima = temperaturaMaxima; }
    public void setTemperaturaMinima(float temperaturaMinima) { this.temperaturaMinima = temperaturaMinima; }
}
