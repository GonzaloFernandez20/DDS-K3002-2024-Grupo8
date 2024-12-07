package Modelo.Dominio.localizacion;

public class AreaDeCobertura {
    private PuntoEnElMapa puntoEnElMapa;
    private Double radio;

    public AreaDeCobertura(PuntoEnElMapa puntoEnElMapa, Double radio) {
        this.puntoEnElMapa = puntoEnElMapa;
        this.radio = radio;
    }

    public boolean areaContiene(Ubicacion ubicacion){
        //FUNCION QUE DETERMINE EN FUNCION DEL PUNTO EN EL MAPA DE LA UBICACION SI CAE DENTRO DEL AREA
        return true;
    }
    public boolean seSolapaCon(AreaDeCobertura otroArea){
        //FUNCION MATEMATICA A IMPLEMENTAR
        return true;
    }

    // ----------> Getters y Setters
    public PuntoEnElMapa getPuntoEnElMapa() { return puntoEnElMapa; }
    public void setPuntoEnElMapa(PuntoEnElMapa puntoEnElMapa) { this.puntoEnElMapa = puntoEnElMapa; }
    public Double getRadio() { return radio; }
    public void setRadio(Double radio) { this.radio = radio; }
}
