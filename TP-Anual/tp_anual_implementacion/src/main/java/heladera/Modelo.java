package heladera;

public class Modelo {
    float temperaturaMaxima;
    float temperaturaMinima;


    public Modelo(float temperaturaMaxima, float temperaturaMinima) {
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
    }

    public boolean controlarTemperatura(float temperatura){
        return temperatura >= temperaturaMinima && temperatura <= temperaturaMinima;
    }

    // ---- Getters y Setters
    public float getTemperaturaMaxima() {
        return temperaturaMaxima;
    }
    public float getTemperaturaMinima() { return temperaturaMinima; }
    public void setTemperaturaMaxima(float temperaturaMaxima) { this.temperaturaMaxima = temperaturaMaxima; }
    public void setTemperaturaMinima(float temperaturaMinima) { this.temperaturaMinima = temperaturaMinima; }
}
