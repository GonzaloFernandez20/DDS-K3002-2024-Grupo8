package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;

public class ViandaDTO {
    private String tipoDeComida;
    private LocalDate fechaDeCaducidad;
    private String calorias;
    private String peso;
    private EstadoVianda estado;

    // Constructores------------------------------------------------
    public ViandaDTO() {
    }

    public ViandaDTO(String tipoDeComida,
                     LocalDate fechaDeCaducidad,
                     String calorias,
                     String peso,
                     EstadoVianda estado)
    {
        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = estado;
    }

    // Getters y setters ------------------------------------------------------------------------------------
    public String getTipoDeComida() { return tipoDeComida; }
    public void setTipoDeComida(String tipoDeComida) { this.tipoDeComida = tipoDeComida; }

    public LocalDate getFechaDeCaducidad() { return fechaDeCaducidad; }
    public void setFechaDeCaducidad(LocalDate fechaDeCaducidad) { this.fechaDeCaducidad = fechaDeCaducidad; }

    public String getCalorias() { return calorias; }
    public void setCalorias(String calorias) { this.calorias = calorias; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public EstadoVianda getEstado() { return estado; }
    public void setEstado(EstadoVianda estado) { this.estado = estado; }

}

