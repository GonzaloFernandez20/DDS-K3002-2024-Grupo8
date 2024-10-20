package Modelo.Dominio.heladera;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Heladera")
public class Heladera {
    @Id
    @GeneratedValue
    private Integer id_heladera;
    @Column(name = "idHeladera")
    private int idHeladera;
    @ManyToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorACargo;
    @ManyToOne
    @JoinColumn(name = "id_ubicacion", referencedColumnName = "id_ubicacion")
    private Ubicacion ubicacion;
    @Column(name = "cantidad_de_viandas")
    private int capacidadDeViandas;
    @Column(name = "fecha_de_puesta_en_funcionamiento")
    private LocalDate puestaEnFuncionamiento;
    @OneToMany
    @JoinColumn(name = "is_vianda", referencedColumnName = "id_vianda")
    private List<Vianda> viandasEnStock;
    @ManyToOne
    @JoinColumn(name = "id_modelo", referencedColumnName = "id_modelo")
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    private EstadoHeladera estado;

    private NotificadorDeSuscriptos notificadorDeSuscriptos;

    // BROKER ------------------------------------------
    private static String BROKER_ADDRESS = "localhost"; // Dirección del broker
    private static int BROKER_PORT = 12345; // Puerto del broker
    // ------------------------------------------------

    public Heladera(Colaborador colaboradorACargo,
                    Ubicacion ubicacion,
                    Integer capacidadDeViandas,
                    Modelo modelo,
                    LocalDate puestaEnFuncionamiento) {

        if(colaboradorACargo == null ){throw new IllegalArgumentException("La heladera requiere de un colaborador a cargo");}
        if(ubicacion == null ){throw new IllegalArgumentException("La ubicacion es obligatoria");}
        if(capacidadDeViandas == null){throw new IllegalArgumentException("La capacidad de la heladera es obligatoria");}
        if(modelo == null){throw new IllegalArgumentException("El modelo es obligatorio");}
        this.colaboradorACargo = colaboradorACargo;
        this.ubicacion = ubicacion;
        this.capacidadDeViandas = capacidadDeViandas;
        this.viandasEnStock = new ArrayList<>();
        this.modelo = modelo;
        this.estado = EstadoHeladera.ACTIVA;
        this.puestaEnFuncionamiento = puestaEnFuncionamiento;
        notificadorDeSuscriptos = new NotificadorDeSuscriptos(this);
    }

    // ------------------------------------------------
    public void recibirVianda(Vianda vianda){
       if(espacioDisponible()>0){
            this.viandasEnStock.add(vianda);
        }
        else{
            throw new ExcepcionHeladeraLlena("La heladera esta llena, no entran más viandas");
        }
    }

    public List<Vianda> retirarViandas(int cantidadARetirar) {
        Vianda viandaActual;
        List<Vianda> viandasARetirar = new ArrayList<>();

        for (int i = 0; i < cantidadARetirar && !viandasEnStock.isEmpty(); i++) {
            viandaActual = viandasEnStock.getFirst();
            viandasARetirar.add(viandaActual);
            viandasEnStock.remove(viandaActual);
        }
        movimientoDeViandasFinalizado();
        return viandasARetirar;
    }

    public int cantViandasEnStock(){ return viandasEnStock.size(); }
    public int espacioDisponible(){return capacidadDeViandas - cantViandasEnStock();}

    public void huboIncidente(){
        estado = EstadoHeladera.INACTIVA;
        notificadorDeSuscriptos.notificar("se produjo una falla");
    }

    public void movimientoDeViandasFinalizado(){
        int viandasQueQuedan = cantViandasEnStock();
        int viandasQueFaltan = espacioDisponible();

        notificadorDeSuscriptos.notificar("quedan " + viandasQueQuedan + " viandas");
        notificadorDeSuscriptos.notificar("faltan " + viandasQueFaltan + " viandas");
    }


    // ---- Getters y Setters
    public Colaborador getColaboradorACargo() { return colaboradorACargo; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public List<Vianda> getViandasEnStock() {return viandasEnStock;}
    public Modelo getModelo() { return modelo; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }
    public EstadoHeladera getEstado() { return estado; }
    public void setEstado(EstadoHeladera estado) { this.estado = estado; }
    public NotificadorDeSuscriptos getNotificadorDeSuscriptos() { return notificadorDeSuscriptos; }
    public double getLatitud(){ return ubicacion.getPunto().getLatitud(); }
    public double getLongitud(){ return ubicacion.getPunto().getLongitud(); }
    public void setNotificadorDeSuscriptos(NotificadorDeSuscriptos notificadorDeSuscriptos) { this.notificadorDeSuscriptos = notificadorDeSuscriptos; }
    public int getCapacidadDeViandas() { return capacidadDeViandas; }
    public LocalDate getPuestaEnFuncionamiento() { return puestaEnFuncionamiento; }
    public int getIdHeladera(){return this.idHeladera;} // TODO: GENERAR UN CODIGO QUE SE ASIGNE LA PRIMERA VEZ QUE SE EJECUTE EL METODO (STRING)
}

