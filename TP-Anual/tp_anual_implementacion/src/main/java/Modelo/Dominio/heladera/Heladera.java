package Modelo.Dominio.heladera;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "Heladera")
public class Heladera {
    @Id
    @GeneratedValue
    private Integer id_heladera;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaborador_a_cargo", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorACargo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion", referencedColumnName = "id_ubicacion")
    private Ubicacion ubicacion;
    @Column(name = "cantidad_de_viandas")
    private int capacidadDeViandas;
    @Column(name = "fecha_de_puesta_en_funcionamiento")
    private LocalDate puestaEnFuncionamiento;
    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Vianda> viandasEnStock;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo", referencedColumnName = "id_modelo")
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    private EstadoHeladera estado;
    @OneToOne(mappedBy = "heladera")
    private NotificadorDeSuscriptos notificadorDeSuscriptos;

    //Constructores ------------------------------------------------------
    public Heladera(Colaborador colaboradorACargo,
                    Ubicacion ubicacion,
                    Integer capacidadDeViandas,
                    Modelo modelo,
                    LocalDate puestaEnFuncionamiento) {

        this.colaboradorACargo = colaboradorACargo;
        this.ubicacion = ubicacion;
        this.capacidadDeViandas = capacidadDeViandas;
        this.viandasEnStock = new ArrayList<>();
        this.modelo = modelo;
        this.estado = EstadoHeladera.ACTIVA;
        this.puestaEnFuncionamiento = puestaEnFuncionamiento;
    }

    public Heladera() {
        this.viandasEnStock = new ArrayList<>();
    }

    // Metodos --------------------------------------------------------------------------------
    public void recibirVianda(Vianda vianda){
        this.viandasEnStock.add(vianda);
    }

    public List<Vianda> retirarViandas(int cantidadARetirar) {
        Vianda viandaActual;
        List<Vianda> viandasARetirar = new ArrayList<>();

        for (int i = 0; i < cantidadARetirar && !viandasEnStock.isEmpty(); i++) {
            viandaActual = viandasEnStock.remove(0); // Usa remove(0) para eliminar directamente
            viandaActual.setHeladera(null); // Desasocia la heladera
            viandasARetirar.add(viandaActual);
        }
        movimientoDeViandasFinalizado();
        return viandasARetirar;
    }

    public void movimientoDeViandasFinalizado(){
        int viandasQueQuedan = cantViandasEnStock();
        int viandasQueFaltan = espacioDisponible();
        loggearViandasHeladera();
        notificadorDeSuscriptos.notificar("Quedan " + viandasQueQuedan + " viandas");
        notificadorDeSuscriptos.notificar("Faltan " + viandasQueFaltan + " viandas");
    }
    public int cantViandasEnStock(){ return viandasEnStock.size(); }
    public int espacioDisponible(){return capacidadDeViandas - cantViandasEnStock();}

    public void huboIncidente(){
        this.estado = EstadoHeladera.INACTIVA;
        log.info("La {} ID:{} cambió su estado a INACTIVA", getNombreDelPunto(), id_heladera);
        notificadorDeSuscriptos.notificar("Se produjo una falla.");
    }

    private void loggearViandasHeladera(){
        if(viandasEnStock.isEmpty()){
            log.info("La {} ID:{} quedó vacia, se han retirado todas las viandas", getNombreDelPunto(), id_heladera);
        }
        else{
            String viandas = viandasEnStock.stream()
                    .map(Vianda::getTipoDeComida) // Obtener el tipo de comida
                    .collect(Collectors.joining(", "));
            log.info("La {} ID:{} cuanta con las siguientes viandas en stock: {}", getNombreDelPunto(), id_heladera, viandas);
        }
    }

    //Getters y Setters ------------------------------------------------------------------------------------------------
    public double getLatitud(){ return ubicacion.getPunto().getLatitud(); }
    public double getLongitud(){ return ubicacion.getPunto().getLongitud(); }
    public String getNombreDelPunto(){return ubicacion.getNombreDelPunto(); }
    public Integer getid_heladera() {if(Objects.isNull(id_heladera)) { return 0; }return id_heladera;}
}

