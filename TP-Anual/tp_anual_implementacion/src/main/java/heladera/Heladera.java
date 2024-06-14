package heladera;

import colaborador.PersonaHumana;
import colaborador.PersonaJuridica;

import java.util.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Heladera {
    PersonaJuridica colaboradorACargo;
    EstadoHeladera estado;
    private final Modelo modelo;
    Float ultimaTemperaturaRegistrada;
    List<Vianda> viandasEnStock;
    private PuntoEstrategico puntoEstrategico;
    private final int capacidadDeViandas;
    private Date puestaEnFuncionamiento;

    public void setColaborador(PersonaJuridica colaborador) { this.colaboradorACargo = colaborador; }
    public PersonaJuridica getColaboradorACargo() { return colaboradorACargo; }

    public void setPuntoEstrategico(PuntoEstrategico puntoEstrategico) { this.puntoEstrategico = puntoEstrategico; }
    public PuntoEstrategico getPuntoEstrategico() { return puntoEstrategico; }

    public void setPuestaEnFuncionamiento(Date puestaEnFuncionamiento) { this.puestaEnFuncionamiento = puestaEnFuncionamiento; }
    public Date getPuestaEnFuncionamiento() { return puestaEnFuncionamiento; }

    public EstadoHeladera getEstado() { return estado; }

    public Modelo getModelo() { return modelo; }

    public List<Vianda> getViandasEnStock() { return viandasEnStock; }

    public int getCapacidadDeViandas() { return capacidadDeViandas; }

    public void recibirViandas(List<Vianda> viandas) { this.viandasEnStock.addAll(viandas); }
    
    public List<Vianda> retirarViandas(int cantidadARetirar) {
        Vianda vianda;
        List<Vianda> viandasARetirar = new ArrayList<>();
        for (int i = 0; i < cantidadARetirar; i++) {
            vianda = viandasEnStock.getFirst();
            vianda.serEntregada();
            viandasARetirar.add(vianda);
            viandasEnStock.remove(vianda);
        }
        return viandasARetirar;
    }

    public void sacarVianda(Vianda vianda) { this.viandasEnStock.remove(vianda); }

    public void setUltimaTemperaturaRegistrada(Float temperatura) { this.ultimaTemperaturaRegistrada = temperatura; }
    public Float getUltimaTemperaturaRegistrada() { return ultimaTemperaturaRegistrada; }

    public void controlarUltimaTemperatura() {
        Float temperaturaMinima = modelo.getTemperaturaMinima();
        Float temperaturaMaxima = modelo.getTemperaturaMaxima();
        
        if(ultimaTemperaturaRegistrada < temperaturaMinima || ultimaTemperaturaRegistrada > temperaturaMaxima) {
            estado = EstadoHeladera.inactiva;
        }
    }

    public void recibirAviso(AvisoIntentoDeRobo aviso) { aviso.notificar(); }

    public Heladera(PersonaJuridica colaboradorACargo, Modelo modelo, Float ultimaTemperaturaRegistrada, List<Vianda> viandasEnStock, PuntoEstrategico puntoEstrategico, int capacidadDeViandas, Date puestaEnFuncionamiento) {
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

class PuntoEstrategico{
    PuntoEnElMapa punto;
    Direccion direccion;
    String ciudad;
    String nombre;

    public PuntoEstrategico(PuntoEnElMapa punto, Direccion direccion, String ciudad, String nombre){
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.punto = punto;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    
    public Direccion getDireccion() { return direccion; }

    public PuntoEnElMapa getPunto() { return punto; }

    public String getCiudad() { return ciudad; }
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
    PersonaJuridica colaboradorACargo;

    public AvisoIntentoDeRobo(PersonaJuridica colaboradorACargo) {
        this.colaboradorACargo = colaboradorACargo;
    }

    void notificar() {
        colaboradorACargo.serNotificado();
    }
}
