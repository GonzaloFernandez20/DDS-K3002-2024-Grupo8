package DTOs;

import Modelo.Dominio.heladera.EstadoHeladera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeladeraEnMapaDTO {
    private String calle;
    private String altura;
    private String ciudad;
    private String nombreDelPunto;
    private double latitud;
    private double longitud;
    private Integer stockActual;
    private Integer capacidad;
    private EstadoHeladera estado;

    public HeladeraEnMapaDTO(String calle, String altura, String ciudad, String nombreDelPunto, double latitud, double longitud, Integer stockActual, Integer capacidad, EstadoHeladera estado) {
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.nombreDelPunto = nombreDelPunto;
        this.latitud = latitud;
        this.longitud = longitud;
        this.stockActual = stockActual;
        this.capacidad = capacidad;
        this.estado = estado;
    }
}