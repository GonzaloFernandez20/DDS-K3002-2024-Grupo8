package tp_anual.tp_anual_implementacion;

import java.util.Date;
import java.util.List;

class GestorDeContribuciones{
    /*     comentario:
                    Este recibe ya instanciada una contribucion y se encarga de efectuar();
                    Se decide que al igual que un colaborador, su instanciacion depende de un form. */
    private Contribucion contribucionActual;
    private Colaborador colaboradorActual;

    //Inyector de dependencias ->constructor
    public GestorDeContribuciones(Contribucion contribucion, Colaborador colaborador){
        this.contribucionActual = contribucion;
        this.colaboradorActual = colaborador;
    }

    private void realizarContribucion(){
        contribucionActual.contribuir();
        colaboradorActual.sumarContribucion(contribucionActual);
    }
}

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected Date fechaDeDonacion;

    public abstract void contribuir();
    public double puntosQueSumaColaborador() { return 0; };
}

class RegistroDePersonasEnSituacionVulnerable extends Contribucion {
    private List<Vinculacion> tarjetaRepartida;

    @Override
    public void contribuir() {
        // Implementación
    }
    public void darDeAlta(Persona persona) {
        // Implementación
    }

    public void entregarTarjeta(Persona persona) {
        // Implementación
    }

}

class DonacionDeDinero extends Contribucion {
    Date fechaDeDonacion;
    double coeficiente = 0.5;
    private float monto;
    private Frecuencia frecuencia;

    @Override
    public void contribuir() {
        // Implementacion
        // Implementacion con la cuenta bancaria/efectivo/tarjerta?????
        // Agragar al diagrama
        DineroDonado.agregar(monto);
    }

    public double puntosQueSumaColaborador() {
        return monto * coeficiente;
    }
}

class DineroDonado{//de instanciación única
    //falta en el diagrama
    float totalDeDinero;
    public void agregar(float monto){
        totalDeDinero += monto;
    }
}

class DonacionDeVianda extends Contribucion {
    private List<Vianda> viandasDonadas;
    private Heladera heladera;
    double coeficiente = 1.5;

    public void agregarViandaADonacion(Vianda vianda) {
        viandasDonadas.add(vianda);
    }

    @Override
    public void contribuir() {
        heladera.recibirViandas(viandasDonadas);
    }

    public double puntosQueSumaColaborador() {
        return viandasDonadas.size() * coeficiente;
    }
}

class DistribucionDeVianda extends Contribucion {
    private Heladera heladeraDeOrigen;
    private Heladera heladeraDestino;
    private int cantDeViandas;
    private MotivoDeDistribucion motivo;
    Date fechaDeDistribucion;
    double coeficiente = 1;

    @Override
    public void contribuir() {
        List <Vianda> viandas = heladeraDeOrigen.retirarViandas(cantDeViandas);
        heladeraDestino.recibirViandas(viandas.forEach(vianda -> vianda.trasladar(heladeraDestino)));
    }

    public double puntosQueSumaColaborador() {
        return cantDeViandas * coeficiente;
    }
}

class HacerseCargoDeHeladera extends Contribucion {
    private Heladera heladeraACargo;
    double coeficiente = 0.5;

    @Override
    public void contribuir() {
        heladeraACargo.setColaborador(colaborador);
    }

    public double puntosQueSumaColaborador() {
        // no por ahora
    }
}

class OfertaDeProductos extends Contribucion {
    String nombreOferta;
    Rubro rubro;
    double puntosNecesarios;
    String imagen;
    Producto producto;

    public void contribuir(){
        //
    }

    public double puntosQueSumaColaborador() {
        return 0;
    }

    public double getPuntosNecesarios() { return puntosNecesarios; }

    public void disminuirStock() { producto.disminuirStock(); }
}

class Producto {
    String nombreProducto;
    int stock;

    public void disminuirStock() { stock--; }
}

enum MotivoDeDistribucion{
    desperfectoHeladera,
    faltaDeViandas
}

enum Rubro {
    GASTRONOMIA,
    ELECTRONICA,
    ARTICULOS_PARA_EL_HOGAR
}

class Imagen {
    private String nombre;
    private String resolucion;
    private String epigrafe;
}

enum Frecuencia {
    UNICAMENTE,
    DIARIAMENTE,
    SEMANALMENTE,
    MENSUALMENTE,
    ANUALMENTE,
    PERSONALIZADO
}