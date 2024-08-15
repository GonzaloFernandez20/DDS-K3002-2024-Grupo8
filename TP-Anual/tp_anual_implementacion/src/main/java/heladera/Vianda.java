package heladera;

import colaborador.Colaborador;
import nuestras_excepciones.ViandaRechazada;
import persona.PersonaHumana;

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
                  LocalDate fechaDeDonacion,
                  Colaborador colaborador,
                  Heladera heladera,
                  String calorias,
                  String peso,
                  EstadoVianda estado) {

        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = estado;
    }

    public void trasladar(Heladera heladeraNueva) throws ViandaRechazada {
        heladera = heladeraNueva;
        List<Vianda> vianda = new ArrayList<>();
        vianda.add(this);
        heladera.recibirViandas(vianda);
    }

    public EstadoVianda getEstado() {
        if(this.fechaDeCaducidad.isBefore(LocalDate.now())){this.setEstadoVianda(EstadoVianda.VENCIDA);}
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

