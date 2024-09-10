package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;

public class FallasPorHeladera {
    private Heladera heladera;
    private Integer cantidadDeFallas;

    public FallasPorHeladera(Heladera heladera, Integer cantidadDeFallas) {
        this.heladera = heladera;
        this.cantidadDeFallas = cantidadDeFallas;
    }
}
