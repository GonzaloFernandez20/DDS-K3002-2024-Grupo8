package heladera;

import colaborador.Colaborador;
import localizacion.Ubicacion;
import nuestras_excepciones.FallaHeladera;
import suscripcion.NotificadorDeSuscriptos;

import java.time.LocalDate;
import java.util.*;


public class Heladera {
    private final Colaborador colaboradorACargo;
    private final Ubicacion ubicacion;
    private final int capacidadDeViandas;
    private LocalDate puestaEnFuncionamiento;
    private List<Vianda> viandasEnStock;
    private Modelo modelo;
    private EstadoHeladera estado;
    private final NotificadorDeSuscriptos notificadorDeSuscriptos;

    // BROKER ------------------------------------------
    private static final String BROKER_ADDRESS = "localhost"; // Dirección del broker
    private static final int BROKER_PORT = 12345; // Puerto del broker
    // ------------------------------------------------

    public Heladera(Colaborador colaboradorACargo,
                    Ubicacion ubicacion,
                    int capacidadDeViandas,
                    Modelo modelo,
                    EstadoHeladera estado,
                    NotificadorDeSuscriptos notificadorDeSuscriptos) {
        this.colaboradorACargo = colaboradorACargo;
        this.ubicacion = ubicacion;
        this.capacidadDeViandas = capacidadDeViandas;
        this.viandasEnStock = new ArrayList<>();
        this.modelo = modelo;
        this.estado = estado;
        this.notificadorDeSuscriptos = notificadorDeSuscriptos;
    }

    // ------------------------------------------------
    public void recibirViandas(List<Vianda> viandas) {
        if(viandasEnStock.size() + viandas.size() <= capacidadDeViandas){
            this.viandasEnStock.addAll(viandas);
        }
    }

    public List<Vianda> retirarViandas(int cantidadARetirar) throws FallaHeladera {
        Vianda vianda;
        List<Vianda> viandasARetirar = new ArrayList<>();
        for (int i = 0; i < cantidadARetirar && !viandasEnStock.isEmpty(); i++) {
            vianda = viandasEnStock.get(0);
            //vianda.serEntregada();
            viandasARetirar.add(vianda);
            viandasEnStock.remove(vianda);
        }
        return viandasARetirar;
        }

    // ---- Getters y Setters
    public Colaborador getColaboradorACargo() { return colaboradorACargo; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public LocalDate getPuestaEnFuncionamiento() { return puestaEnFuncionamiento; }
    public void setPuestaEnFuncionamiento(LocalDate puestaEnFuncionamiento) { this.puestaEnFuncionamiento = puestaEnFuncionamiento; }
    public Modelo getModelo() { return modelo; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }
    public EstadoHeladera getEstado() { return estado; }
    public NotificadorDeSuscriptos getNotificadorDeSuscriptos() { return notificadorDeSuscriptos; }
}

//public void sacarVianda(Vianda vianda) { this.viandasEnStock.remove(vianda); } -> TODO: Verificar si trae problemas devolver una lista de 1 elemento
/*    public void controlarUltimaTemperatura() {
        if (ultimaTemperaturaRegistrada != null) {
            Float temperaturaMinima = modelo.getTemperaturaMinima();
            Float temperaturaMaxima = modelo.getTemperaturaMaxima();

            if (ultimaTemperaturaRegistrada < temperaturaMinima || ultimaTemperaturaRegistrada > temperaturaMaxima) {
                estado = EstadoHeladera.inactiva;
                sistema.serAlertado(this, TEMPERATURA);
            } else {
                estado = EstadoHeladera.activa;
            }
        }
    }*/
