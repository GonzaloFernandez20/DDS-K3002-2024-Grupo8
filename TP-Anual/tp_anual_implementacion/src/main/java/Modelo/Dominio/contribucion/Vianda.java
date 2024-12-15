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
    private String tipoDeComida;
    @Column(name = "fecha_de_caducidad")
    private LocalDate fechaDeCaducidad;
    @Column(name = "fecha_de_donacion")
    private LocalDate fechaDeDonacion;
    @ManyToOne
    @JoinColumn(name = "colaborador", referencedColumnName = "id_colaborador")
    private Colaborador colaborador;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;
    @Column(name = "calorias")
    private String calorias;
    @Column(name = "peso")
    private String peso;
    @Enumerated(EnumType.STRING)
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

    public Vianda() {

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

    public void setTipoDeComida(String tipoDeComida) {
        this.tipoDeComida = tipoDeComida;
    }

    public void setFechaDeCaducidad(LocalDate fechaDeCaducidad) {
        this.fechaDeCaducidad = fechaDeCaducidad;
    }

    public void setFechaDeDonacion(LocalDate fechaDeDonacion) {
        this.fechaDeDonacion = fechaDeDonacion;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setEstado(EstadoVianda estado) {
        this.estado = estado;
    }
}

