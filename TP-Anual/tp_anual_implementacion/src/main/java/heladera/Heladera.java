package heladera;

import colaborador.Colaborador;
import localizacion.Direccion;
import localizacion.PuntoEnElMapa;
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
        Float temperaturaMinima = modelo.getTemperaturaMinima();
        Float temperaturaMaxima = modelo.getTemperaturaMaxima();
        
        if(ultimaTemperaturaRegistrada < temperaturaMinima || ultimaTemperaturaRegistrada > temperaturaMaxima) {
            estado = EstadoHeladera.inactiva;
        }
    }

    public void recibirAviso(AvisoIntentoDeRobo aviso) { aviso.notificar(); }

    public Heladera(Colaborador colaboradorACargo, Modelo modelo, Float ultimaTemperaturaRegistrada, List<Vianda> viandasEnStock, Ubicacion puntoEstrategico, int capacidadDeViandas, Date puestaEnFuncionamiento) {
        this.colaboradorACargo = colaboradorACargo;
        this.estado = EstadoHeladera.activa;
        this.modelo = modelo;
        this.ultimaTemperaturaRegistrada = ultimaTemperaturaRegistrada;
        this.viandasEnStock = viandasEnStock;
        this.puntoEstrategico = puntoEstrategico;
        this.capacidadDeViandas = capacidadDeViandas;
        this.puestaEnFuncionamiento = puestaEnFuncionamiento;
    }
}

enum EstadoHeladera {
    activa,
    inactiva
}

class Modelo {
    Float maximaTemperatura;
    Float minimaTemperatura;

    public Float getTemperaturaMinima() { return minimaTemperatura; }
    public Float getTemperaturaMaxima() { return maximaTemperatura; }
}

class SensoreoDeTemperatura {
    Float temperaturaRegistrada;
    Heladera heladera;

    void avisoDeTemperaturaActualizada() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                heladera.setUltimaTemperaturaRegistrada(temperaturaRegistrada);
            }
        };
        timer.scheduleAtFixedRate(task, 0, 300000);
    }
}

class SensoreoDeMovimiento {
    AvisoIntentoDeRobo aviso;
    Heladera heladera;

    void enviarAlerta() {
        heladera.recibirAviso(aviso);
    }
}

class AvisoIntentoDeRobo {
    Colaborador colaboradorACargo;

    public AvisoIntentoDeRobo(Colaborador colaboradorACargo) {
        this.colaboradorACargo = colaboradorACargo;
    }

    void notificar() {
        colaboradorACargo.serNotificado();
    }
}
