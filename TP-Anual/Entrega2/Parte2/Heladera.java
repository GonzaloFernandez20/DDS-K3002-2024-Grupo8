package TPAnual;

import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class Heladera {
    PersonaJuridica colaboradorACargo;
    EstadoHeladera estado = EstadoHeladera.activa;
    Modelo modelo;
    Float ultimaTemperaturaRegistrada;
    List<Vianda> viandasEnStock;
    private PuntoEstrategico puntoEstrategico;
    private int capacidadDeViandas;
    private Date puestaEnFuncionamiento;

    public void recibirViandas(List<Vianda> viandas) {
        viandasEnStock.addAll(viandas);
    }

    public void setColaborador(PersonaJuridica colaborador) {
        colaboradorACargo = colaborador;
    }

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

    public void sacarVianda(Vianda vianda) {
        viandasEnStock.remove(vianda);
    }

    public void setUltimaTemperaturaRegistrada(Float temperatura) {
        ultimaTemperaturaRegistrada = temperatura;
    }

    public void controlarUltimaTemperatura() {
        Float temperaturaMinima = modelo.getTemperaturaMinima();
        Float temperaturaMaxima = modelo.getTemperaturaMaxima();
        if(ultimaTemperaturaRegistrada < temperaturaMinima || ultimaTemperaturaRegistrada > temperaturaMaxima) {
            estado = EstadoHeladera.inactiva;
        }
    }

    public void recibirAviso(AvisoIntentoDeRobo aviso) {
        aviso.notificar();
    }
}

enum EstadoHeladera {
    activa,
    inactiva
}

class Modelo {
    Float maximaTemperatura;
    Float minimaTemperatura;

    public Float getTemperaturaMinima() { return minimaTemperatura;}
    public Float getTemperaturaMaxima() { return maximaTemperatura;}
}

class PuntoEstrategico{
    PuntoEnElMapa punto;
    Direccion direccion;
    String ciudad;
    String nombre;
}

class PuntoEnElMapa {
    double latitud;
    double longitud;
}

class Direccion{
    String calle;
    int altura;
    int codPostal;
    String unidadFuncional;
}

class RecomendacionPuntoDeColocacion {
    PuntoEnElMapa punto;
    double radio;

    List<PuntoEnElMapa> consultarPuntosDeColocacion() {
        // lo delegamos a la API
    }
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

    void notificar() {
        colaboradorACargo.serNotificado();
    }
}

