package DTOs;

import Modelo.Dominio.heladera.EstadoHeladera;

public class HeladeraSeleccionDTO {
    private String nombreDelPunto;
    private String calle;
    private String altura;
    private String ciudad;
    private int capacidadRestante;
    private EstadoHeladera estado;


    public HeladeraSeleccionDTO(String nombreDelPunto,
                                String calle,
                                String altura,
                                String ciudad,
                                int capacidadRestante,
                                EstadoHeladera estado) {
        this.nombreDelPunto = nombreDelPunto;
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.capacidadRestante = capacidadRestante;
        this.estado = estado;
    }
}
