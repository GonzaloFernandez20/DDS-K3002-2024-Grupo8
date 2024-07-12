package heladera;

import colaborador.Colaborador;
import localizacion.Ubicacion;
import java.util.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Heladera {
    Colaborador colaboradorACargo;
    EstadoHeladera estado;
    private final Modelo modelo;
    Float ultimaTemperaturaRegistrada;
    List<Vianda> viandasEnStock;
    private Ubicacion puntoEstrategico;
    private final int capacidadDeViandas;
    private Date puestaEnFuncionamiento;

    public void setColaborador(Colaborador colaborador) { this.colaboradorACargo = colaborador; }
    public Colaborador getColaboradorACargo() { return colaboradorACargo; }

    public void setUbicacion(Ubicacion puntoEstrategico) { this.puntoEstrategico = puntoEstrategico; }
    public Ubicacion getUbicacion() { return puntoEstrategico; }

    public void setPuestaEnFuncionamiento(Date puestaEnFuncionamiento) { this.puestaEnFuncionamiento = puestaEnFuncionamiento; }
    public Date getPuestaEnFuncionamiento() { return puestaEnFuncionamiento; }

    public EstadoHeladera getEstado() { return estado; }

    public Modelo getModelo() { return modelo; }

    public List<Vianda> getViandasEnStock() { return viandasEnStock; }

    public int getCapacidadDeViandas() { return capacidadDeViandas; }

    public void setUltimaTemperaturaRegistrada(Float temperatura) { this.ultimaTemperaturaRegistrada = temperatura; }
    public Float getUltimaTemperaturaRegistrada() { return ultimaTemperaturaRegistrada; }

    public void recibirViandas(List<Vianda> viandas) { this.viandasEnStock.addAll(viandas); }
    
    public List<Vianda> retirarViandas(int cantidadARetirar) {
        Vianda vianda;
        List<Vianda> viandasARetirar = new ArrayList<>();
        for (int i = 0; i < cantidadARetirar; i++) {
            vianda = viandasEnStock.get(0);
            vianda.serEntregada();
            viandasARetirar.add(vianda);
            viandasEnStock.remove(vianda);
        }
        return viandasARetirar;
    }

    public void sacarVianda(Vianda vianda) { this.viandasEnStock.remove(vianda); }

    public void controlarUltimaTemperatura() {
        Float temperaturaMinima = modelo.getMinimaTemperatura();
        Float temperaturaMaxima = modelo.getMaximaTemperatura();
        
        if(ultimaTemperaturaRegistrada < temperaturaMinima || ultimaTemperaturaRegistrada > temperaturaMaxima) {
            estado = EstadoHeladera.inactiva;
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
}

