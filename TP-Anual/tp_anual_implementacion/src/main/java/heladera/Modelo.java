package heladera;

public class Modelo {
    Float temperaturaMaxima;
    Float temperaturaMinima;

    public Float getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(Float temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public Float getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(Float temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public Modelo(Float temperaturaMaxima, Float temperaturaMinima) {
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
    }
}
