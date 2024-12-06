package Modelo.Dominio.heladera;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Heladera")
public class Heladera {
    @Id
    @GeneratedValue
    private Integer id_heladera;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaborador_a_cargo", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorACargo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ubicacion", referencedColumnName = "id_ubicacion")
    private Ubicacion ubicacion;
    @Column(name = "cantidad_de_viandas")
    private int capacidadDeViandas;
    @Column(name = "fecha_de_puesta_en_funcionamiento")
    private LocalDate puestaEnFuncionamiento;
    @OneToMany(mappedBy = "heladera", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Vianda> viandasEnStock;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modelo", referencedColumnName = "id_modelo")
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    private EstadoHeladera estado;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "notificador_de_suscriptos", referencedColumnName = "id_notificador_de_suscriptos")
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
        notificadorDeSuscriptos = new NotificadorDeSuscriptos(this);
    }

    public Heladera() {
        this.viandasEnStock = new ArrayList<>();
    }

    // Metodos --------------------------------------------------------------------------------
    public int capacidadRestante(){
        return capacidadDeViandas - viandasEnStock.size();
    }

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
        notificadorDeSuscriptos.notificar("Se produjo una falla.");
    }

    public void movimientoDeViandasFinalizado(){
        int viandasQueQuedan = cantViandasEnStock();
        int viandasQueFaltan = espacioDisponible();

        notificadorDeSuscriptos.notificar("Quedan " + viandasQueQuedan + " viandas.");
        notificadorDeSuscriptos.notificar("Faltan " + viandasQueFaltan + " viandas.");
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

    public Integer getid_heladera() {
        if(Objects.isNull(id_heladera)) { return 0; } // No compila sino
        return id_heladera;
    }

    public void setColaboradorACargo(Colaborador colaboradorACargo) {
        this.colaboradorACargo = colaboradorACargo;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCapacidadDeViandas(int capacidadDeViandas) {
        this.capacidadDeViandas = capacidadDeViandas;
    }

    public void setPuestaEnFuncionamiento(LocalDate puestaEnFuncionamiento) {
        this.puestaEnFuncionamiento = puestaEnFuncionamiento;
    }

    public int getCantViandasEnStock() {
        return viandasEnStock.size();
    }
}

