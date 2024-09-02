package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import nuestras_excepciones.ViandaRechazada;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

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
    }

    public EstadoVianda getEstado() {
        if(this.fechaDeCaducidad.isBefore(LocalDate.now())){ this.estado = EstadoVianda.VENCIDA; }
        return estado;
    }

    // Getters y Setters
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

