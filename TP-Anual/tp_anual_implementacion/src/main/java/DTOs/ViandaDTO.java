package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;

public class ViandaDTO {
    private String tipoDeComida;
    private LocalDate fechaDeCaducidad;
    private LocalDate fechaDeDonacion;
    private Colaborador colaborador;
    private Heladera heladera;
    private String calorias;
    private String peso;
    private EstadoVianda estado;

    // Constructor vacío (necesario para la deserialización de JSON)
    public ViandaDTO() {
    }

    // Constructor con todos los parámetros
    public ViandaDTO(String tipoDeComida,
                     LocalDate fechaDeCaducidad,
                     LocalDate fechaDeDonacion,
                     Colaborador colaborador,
                     Heladera heladera,
                     String calorias,
                     String peso,
                     EstadoVianda estado)
    {
        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = estado;
    }

    // Getters y setters
    public String getTipoDeComida() { return tipoDeComida; }
    public void setTipoDeComida(String tipoDeComida) { this.tipoDeComida = tipoDeComida; }
    public LocalDate getFechaDeCaducidad() { return fechaDeCaducidad; }
    public void setFechaDeCaducidad(LocalDate fechaDeCaducidad) { this.fechaDeCaducidad = fechaDeCaducidad; }
    public LocalDate getFechaDeDonacion() { return fechaDeDonacion; }
    public void setFechaDeDonacion(LocalDate fechaDeDonacion) { this.fechaDeDonacion = fechaDeDonacion; }
    public Colaborador getColaborador() { return colaborador; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }
    public Heladera getHeladera() { return heladera; }
    public void setHeladera(Heladera heladera) { this.heladera = heladera; }
    public String getCalorias() { return calorias; }
    public void setCalorias(String calorias) { this.calorias = calorias; }
    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }
    public EstadoVianda getEstado() { return estado; }
    public void setEstado(EstadoVianda estado) { this.estado = estado; }
}

