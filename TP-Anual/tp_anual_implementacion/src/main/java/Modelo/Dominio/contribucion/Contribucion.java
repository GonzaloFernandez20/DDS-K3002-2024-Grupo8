package Modelo.Dominio.contribucion;

import java.time.LocalDate;

import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;

@Entity
@Table(name = "Contribucion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contribucion {
    @Id
    @GeneratedValue
    private Integer id_contribucion;
    @ManyToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    protected Colaborador colaborador;
    @Column(name = "fecha_de_contribucion")
    protected LocalDate fechaDeContribucion;

    public abstract void procesarLaContribucion();
    public abstract double puntosQueSumaColaborador();
}

