package Modelo.Dominio.heladera;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.incidentes.TipoAlerta;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Excepciones.ExcepcionHeladeraLlena;

import java.time.LocalDate;
import java.util.*;


public class Heladera {
    private final Colaborador colaboradorACargo;
    private final Ubicacion ubicacion;
    private final int capacidadDeViandas;
    private final LocalDate puestaEnFuncionamiento;
    private final List<Vianda> viandasEnStock;
    private Modelo modelo;
    private EstadoHeladera estado;
    private NotificadorDeSuscriptos notificadorDeSuscriptos;
    private Float temperatura = modelo.temperaturaMinima + 1;

    // BROKER ------------------------------------------
    private static final String BROKER_ADDRESS = "localhost"; // Dirección del broker
    private static final int BROKER_PORT = 12345; // Puerto del broker
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

    public void actualizar(double temperatura){
        if(! modelo.controlarTemperatura(temperatura)) {
            GestorDeIncidentes.reportarAlerta(this, TipoAlerta.TEMPERATURA);
            setEstado(EstadoHeladera.INACTIVA);
        }
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

    public Float getTemperatura() {
        return temperatura;
    }

    public void setNotificadorDeSuscriptos(NotificadorDeSuscriptos notificadorDeSuscriptos) { this.notificadorDeSuscriptos = notificadorDeSuscriptos; }
}

