package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
@Getter
@Setter
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
    @ManyToOne
    @JoinColumn(name = "heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;
    @Column(name = "calorias")
    private String calorias;
    @Column(name = "peso")
    private String peso;
    @Enumerated(EnumType.STRING)
    private EstadoVianda estado;

    //Constructores --------------------------------------------------------------------------------------------------
    public Vianda(String tipoDeComida,
                  LocalDate fechaDeCaducidad,
                  Colaborador colaborador,
                  @Nullable String calorias,
                  @Nullable String peso) {
        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.fechaDeDonacion = LocalDate.now();
        this.colaborador = colaborador;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = EstadoVianda.NO_ENTREGADA;
    }

    public Vianda() {}

    //Metodos ----------------------------------------------------------------------------------------------------------
    public void trasladar() {
       this.heladera = null;
       this.estado = EstadoVianda.EN_TRASLADO;
    }

    public void ingresarEn(Heladera heladeraNueva) {
        this.estado = EstadoVianda.ENTREGADA;
        this.heladera = heladeraNueva;
    }

    public void consumir(){
        this.estado = EstadoVianda.RETIRADA;
    }

    //Getter -----------------------------------------------------------------------------------------------------------
    public EstadoVianda getEstado() {
        if(this.fechaDeCaducidad.isBefore(LocalDate.now())){ this.estado = EstadoVianda.VENCIDA; }
        return estado;
    }

}

