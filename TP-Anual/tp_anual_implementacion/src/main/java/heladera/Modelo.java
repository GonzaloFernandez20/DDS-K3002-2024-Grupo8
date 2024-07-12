package heladera;

public class Modelo {
    Float maximaTemperatura;
    Float minimaTemperatura;

    public Modelo(Float maximaTemperatura, Float minimaTemperatura) {
        this.maximaTemperatura = maximaTemperatura;
        this.minimaTemperatura = minimaTemperatura;
    }

    public Float getMaximaTemperatura() {
        return maximaTemperatura;
    }

    public void setMaximaTemperatura(Float maximaTemperatura) {
        this.maximaTemperatura = maximaTemperatura;
    }

    public Float getMinimaTemperatura() {
        return minimaTemperatura;
    }

    public void setMinimaTemperatura(Float minimaTemperatura) {
        this.minimaTemperatura = minimaTemperatura;
    }
}
