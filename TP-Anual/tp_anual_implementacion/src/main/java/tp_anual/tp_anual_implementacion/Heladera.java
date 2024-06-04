package tp_anual.tp_anual_implementacion;

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

    public void recibirViandas(List<Vianda> viandas) {
        viandasEnStock.addAll(viandas);
    }

    public void setColaborador(PersonaJuridica colaborador) {
        colaboradorACargo = colaborador;
    }

    public void setPuntoEstrategico(PuntoEstrategico puntoEstrategico) { this.puntoEstrategico = puntoEstrategico; }

    public void setPuestaEnFuncionamiento(Date puestaEnFuncionamiento) { this.puestaEnFuncionamiento = puestaEnFuncionamiento; }

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


    public PersonaJuridica getColaboradorACargo() {
        return colaboradorACargo;
    }

    public EstadoHeladera getEstado() {
        return estado;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public Float getUltimaTemperaturaRegistrada() {
        return ultimaTemperaturaRegistrada;
    }

    public List<Vianda> getViandasEnStock() {
        return viandasEnStock;
    }

    public PuntoEstrategico getPuntoEstrategico() {
        return puntoEstrategico;
    }

    public int getCapacidadDeViandas() {
        return capacidadDeViandas;
    }

    public Date getPuestaEnFuncionamiento() {
        return puestaEnFuncionamiento;
    }

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

    public PuntoEstrategico(PuntoEnElMapa punto,
                            Direccion direccion,
                            String ciudad,
                            String nombre){
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.punto = punto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public PuntoEnElMapa getPunto() {
        return punto;
    }

    public String getCiudad() {
        return ciudad;
    }
}

class PuntoEnElMapa {
    double latitud;
    double longitud;

    public PuntoEnElMapa(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
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

/*    List<PuntoEnElMapa> consultarPuntosDeColocacion() {
        // lo delegamos a la API
    }*/
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

class Vianda {
    private String tipoDeComida;
    private Date fechaDeCaducidad;
    private Date fechaDeDonacion;
    private PersonaHumana colaborador;
    private Heladera heladera;
    private Float calorias;
    private Float peso;
    private EstadoVianda estado;

    public void trasladar(Heladera heladeraNueva){
        heladera = heladeraNueva;
        List<Vianda> vianda = new ArrayList<>();
        vianda.add(this);
        heladera.recibirViandas(vianda);
    }

    public void serEntregada() {
        estado = EstadoVianda.entregada;
    }

    public String getTipoDeComida() {
        return tipoDeComida;
    }

    public Date getFechaDeCaducidad() {
        return fechaDeCaducidad;
    }

    public Date getFechaDeDonacion() {
        return fechaDeDonacion;
    }

    public PersonaHumana getColaborador() {
        return colaborador;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public Float getCalorias() {
        return calorias;
    }

    public Float getPeso() {
        return peso;
    }

    public EstadoVianda getEstado() {
        return estado;
    }

    public Vianda(String tipoDeComida, Date fechaDeCaducidad, Date fechaDeDonacion, PersonaHumana colaborador, Heladera heladera, Float calorias, Float peso, EstadoVianda estado) {
        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.fechaDeDonacion = fechaDeDonacion;
        this.colaborador = colaborador;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estado = estado;
    }
}

enum EstadoVianda {
    entregada,
    noEntregada,
    vencida
}