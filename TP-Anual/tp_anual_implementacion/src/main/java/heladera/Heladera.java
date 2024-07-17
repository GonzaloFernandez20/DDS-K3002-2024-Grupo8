package heladera;

import colaborador.Colaborador;
import localizacion.Ubicacion;
import nuestras_excepciones.FallaHeladera;
import nuestras_excepciones.ViandaRechazada;
import sistema.Sistema;

import java.util.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static sistema.TipoAlerta.TEMPERATURA;

public class Heladera {
    private Colaborador colaboradorACargo;
    private EstadoHeladera estado;
    private final Modelo modelo;
    Float ultimaTemperaturaRegistrada;
    List<Vianda> viandasEnStock;
    private Ubicacion puntoEstrategico;
    private final int capacidadDeViandas;
    private Date puestaEnFuncionamiento;
    private Sistema sistema;

    private static final String BROKER_ADDRESS = "localhost"; // Dirección del broker
    private static final int BROKER_PORT = 12345; // Puerto del broker

    public void setColaborador(Colaborador colaborador) { this.colaboradorACargo = colaborador; }
    public Colaborador getColaboradorACargo() { return colaboradorACargo; }

    public void setUbicacion(Ubicacion puntoEstrategico) { this.puntoEstrategico = puntoEstrategico; }
    public Ubicacion getUbicacion() { return puntoEstrategico; }

    public void setSistema(Sistema sistema) { this.sistema = sistema; }

    public void setPuestaEnFuncionamiento(Date puestaEnFuncionamiento) { this.puestaEnFuncionamiento = puestaEnFuncionamiento; }
    public Date getPuestaEnFuncionamiento() { return puestaEnFuncionamiento; }

    public EstadoHeladera getEstado() { return estado; }

    public Modelo getModelo() { return modelo; }

    public List<Vianda> getViandasEnStock() { return viandasEnStock; }

    public int getCapacidadDeViandas() { return capacidadDeViandas; }

    public void setUltimaTemperaturaRegistrada(Float temperatura) {
        this.ultimaTemperaturaRegistrada = temperatura;
        controlarUltimaTemperatura();
        if (sistema != null) {
            sistema.recibirTemperatura(temperatura, this); // Envía la temperatura al sistema
        }
    }

    public Float getUltimaTemperaturaRegistrada() { return ultimaTemperaturaRegistrada; }

    public void recibirViandas(List<Vianda> viandas) throws ViandaRechazada {
        if(viandasEnStock.size() + viandas.size() <= capacidadDeViandas){
            this.viandasEnStock.addAll(viandas);
        }
        else {
            throw new ViandaRechazada("La heladera está llena");
        }
    }
    
    public List<Vianda> retirarViandas(int cantidadARetirar) throws FallaHeladera {
        Vianda vianda;
        List<Vianda> viandasARetirar = new ArrayList<>();
        for (int i = 0; i < cantidadARetirar && !viandasEnStock.isEmpty(); i++) {
            vianda = viandasEnStock.get(0);
            vianda.serEntregada();
            viandasARetirar.add(vianda);
            viandasEnStock.remove(vianda);
        }
        return viandasARetirar;
        }

    public void sacarVianda(Vianda vianda) { this.viandasEnStock.remove(vianda); }

    public void controlarUltimaTemperatura() {
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
    }

    public void recibirAviso(AvisoIntentoDeRobo aviso) { aviso.notificar(); }

    public Heladera(Colaborador colaboradorACargo, Modelo modelo, List<Vianda> viandasEnStock, Ubicacion puntoEstrategico, int capacidadDeViandas, Date puestaEnFuncionamiento) {
        this.colaboradorACargo = colaboradorACargo;
        this.estado = EstadoHeladera.activa;
        this.modelo = modelo;
        this.viandasEnStock = viandasEnStock;
        this.puntoEstrategico = puntoEstrategico;
        this.capacidadDeViandas = capacidadDeViandas;
        this.puestaEnFuncionamiento = puestaEnFuncionamiento;
    }

    public void setEstado(EstadoHeladera estado) {
        this.estado = estado;
    }
}
