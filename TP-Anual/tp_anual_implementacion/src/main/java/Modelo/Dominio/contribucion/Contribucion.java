package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "Contribucion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contribucion {
    @Id
    @GeneratedValue
    private Integer id_contribucion;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "colaborador")
    protected Colaborador colaborador;
    @Column(name = "fecha_de_contribucion")
    protected LocalDate fechaDeContribucion;

    //Constructor ------------------------
    public Contribucion() {}

    //Metodos -------------------------------------------------------------------------------
    public abstract void procesarLaContribucion();
    public abstract double puntosQueSumaColaborador();

}

