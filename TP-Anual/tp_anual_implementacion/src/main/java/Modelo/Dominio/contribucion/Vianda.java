package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public class Vianda {
    private final String tipoDeComida;
    private final LocalDate fechaDeCaducidad;
    private final LocalDate fechaDeDonacion;
    private final Colaborador colaborador;
    private Heladera heladera;
    private final String calorias;
    private final String peso;
    private EstadoVianda estado;

    public Vianda(String tipoDeComida,
                  LocalDate fechaDeCaducidad,
                  Colaborador colaborador,
                  Heladera heladera,
                  @Nullable String calorias,
                  @Nullable String peso) {
        if(tipoDeComida == null){throw new IllegalArgumentException("El tipo de comida es obligatorio");}
        if(fechaDeCaducidad == null){throw new IllegalArgumentException("La fecha de caducidad es obligatoria");}
        if(colaborador == null){throw new IllegalArgumentException("El colaborador es obligatorio");}
        if(heladera == null){throw new IllegalArgumentException("La heladera es obligatoria");}

        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.fechaDeDonacion = LocalDate.now();
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = EstadoVianda.NO_ENTREGADA;
    }

    public void trasladar(Heladera heladeraNueva) {
        heladera = heladeraNueva;
        if(!heladeraNueva.equals(heladera)){
            this.estado = EstadoVianda.EN_TRASLADO;
        }
    }

    public EstadoVianda getEstado() {
        if(this.fechaDeCaducidad.isBefore(LocalDate.now())){ this.estado = EstadoVianda.VENCIDA; }
        return estado;
    }

    // ---- Getters y Setters
    public void setEstadoVianda(EstadoVianda estado) { this.estado = estado; }
    public String getTipoDeComida() {
        return tipoDeComida;
    }
    public LocalDate getFechaDeCaducidad() {
        return fechaDeCaducidad;
    }
    public LocalDate getFechaDeDonacion() {
        return fechaDeDonacion;
    }
    public Colaborador getColaborador() {
        return colaborador;
    }
    public Heladera getHeladera() {
        return heladera;
    }
    public String getCalorias() {
        return calorias;
    }
    public String getPeso() {
        return peso;
    }

}

