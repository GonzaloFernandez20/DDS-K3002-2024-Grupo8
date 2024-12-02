package DTOs;

import Modelo.Dominio.heladera.EstadoHeladera;

public class HeladeraSeleccionDTO {
    private int idHeladera;
    private String nombreDelPunto;
    private String calle;
    private String altura;
    private String ciudad;
    private int capacidadRestante;
    private EstadoHeladera estado;


    public HeladeraSeleccionDTO(int idHeladera,
                                String nombreDelPunto,
                                String calle,
                                String altura,
                                String ciudad,
                                int capacidadRestante,
                                EstadoHeladera estado) {
        this.idHeladera=idHeladera;
        this.nombreDelPunto = nombreDelPunto;
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.capacidadRestante = capacidadRestante;
        this.estado = estado;
    }

    public int getIdHeladera() {
        return idHeladera;
    }

    public void setIdHeladera(int idHeladera) {
        this.idHeladera = idHeladera;
    }

    public String getNombreDelPunto() {
        return nombreDelPunto;
    }

    public void setNombreDelPunto(String nombreDelPunto) {
        this.nombreDelPunto = nombreDelPunto;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidadRestante() {
        return capacidadRestante;
    }

    public void setCapacidadRestante(int capacidadRestante) {
        this.capacidadRestante = capacidadRestante;
    }

    public EstadoHeladera getEstado() {
        return estado;
    }

    public void setEstado(EstadoHeladera estado) {
        this.estado = estado;
    }
}
