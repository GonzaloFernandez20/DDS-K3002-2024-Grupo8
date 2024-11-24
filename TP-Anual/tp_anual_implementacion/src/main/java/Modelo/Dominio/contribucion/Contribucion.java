package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Contribucion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contribucion {
    @Id
    @GeneratedValue
    private Integer id_contribucion;
    @ManyToOne
    @JoinColumn(name = "colaborador", referencedColumnName = "id_colaborador")
    protected Colaborador colaborador;
    @Column(name = "fecha_de_contribucion")
    protected LocalDate fechaDeContribucion;

    public abstract void procesarLaContribucion();
    public abstract double puntosQueSumaColaborador();

    public Contribucion() {
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public LocalDate getFechaDeContribucion() {
        return fechaDeContribucion;
    }

    public void setFechaDeContribucion(LocalDate fechaDeContribucion) {
        this.fechaDeContribucion = fechaDeContribucion;
    }
}

