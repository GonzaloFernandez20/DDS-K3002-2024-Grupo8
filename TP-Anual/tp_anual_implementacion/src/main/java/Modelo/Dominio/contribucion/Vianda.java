package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
@Entity
@Table(name = "Vianda")
public class Vianda {
    @Id
    @GeneratedValue
    private Integer id_vianda;
    @Column(name = "tipo_de_comida")
    private final String tipoDeComida;
    @Column(name = "fecha_de_caducidad")
    private final LocalDate fechaDeCaducidad;
    @Column(name = "fecha_de_donacion")
    private final LocalDate fechaDeDonacion;
    @OneToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    private final Colaborador colaborador;
    @OneToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;
    @Column(name = "calorias")
    private final String calorias;
    @Column(name = "peso")
    private final String peso;
    @Enumerated(EnumType.STRING)
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
        if(!heladera.equals(heladeraNueva)){
            this.estado = EstadoVianda.EN_TRASLADO;
        }
        heladera = heladeraNueva;
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

