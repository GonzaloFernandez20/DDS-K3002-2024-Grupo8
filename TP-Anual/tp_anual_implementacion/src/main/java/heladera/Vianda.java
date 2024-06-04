package heladera;

import colaborador.PersonaHumana;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Vianda {
    private String tipoDeComida;
    private LocalDate fechaDeCaducidad;
    private LocalDate fechaDeDonacion;
    private PersonaHumana colaborador;
    private Heladera heladera;
    private Float calorias;
    private Float peso;
    private EstadoVianda estado;

    public void trasladar(Heladera heladeraNueva){
        heladera = heladeraNueva;
        List<Vianda> vianda = new ArrayList<>();
        vianda.add(this);
        heladera.recibirViandas(vianda);
    }

    public void serEntregada() {
        estado = EstadoVianda.entregada;
    }

    public String getTipoDeComida() {
        return tipoDeComida;
    }

    public LocalDate getFechaDeCaducidad() {
        return fechaDeCaducidad;
    }

    public LocalDate getFechaDeDonacion() {
        return fechaDeDonacion;
    }

    public PersonaHumana getColaborador() {
        return colaborador;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public Float getCalorias() {
        return calorias;
    }

    public Float getPeso() {
        return peso;
    }

    public EstadoVianda getEstado() {
        return estado;
    }

    public Vianda(String tipoDeComida, LocalDate fechaDeCaducidad, LocalDate fechaDeDonacion, PersonaHumana colaborador, Heladera heladera, Float calorias, Float peso, EstadoVianda estado) {
        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = estado;
    }
}

enum EstadoVianda {
    entregada,
    noEntregada,
    vencida
}