package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;


public class FallasPorHeladera {
    private Heladera heladera;
    private Integer cantidadDeFallas;

    public FallasPorHeladera(Heladera heladera, Integer cantidadDeFallas) {
        this.heladera = heladera;
        this.cantidadDeFallas = cantidadDeFallas;
    }

    public FallasPorHeladera() {
    }

    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public void setCantidadDeFallas(Integer cantidadDeFallas) {
        this.cantidadDeFallas = cantidadDeFallas;
    }

    public Heladera getHeladera() { return heladera; }
    public Integer getCantidadDeFallas() { return cantidadDeFallas; }
}
