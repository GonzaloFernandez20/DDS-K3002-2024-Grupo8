package tp_anual.tp_anual_implementacion;


import java.util.Date;
import java.util.List;
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.*;

class GestorDeContribuciones{
    /*     comentario:
                    Este recibe ya instanciada una contribucion y se encarga de efectuar();
                    Se decide que al igual que un colaborador, su instanciacion depende de un form. */
    //estos atributos deberían ser pétreos porque la asociación entre colaborador y contribución no puede cambiar
    private final Contribucion contribucionActual;
    private final Colaborador colaboradorActual;

    //Inyector de dependencias ->constructor
    //no va porque se llenan los campos al instanciarse
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
    //Esto habría que volver a modelarlo porque no veo cómo sería implementado de ésta manera
    private List<Vinculacion> tarjetasRepartidas;

    @Override
    public void contribuir() {
        // No entiendo cómo implementaríamos esto me parece que nos falta una clase porque contribuir no tiene argumentos
    }
    public void darDeAlta(PersonaSituacionVulnerable persona, AccesoAHeladeras accesoAHeladeras) {
        //NO ESTA BIEN IMPLEMENTADO
//        Vinculacion nuevaVinculacion = new Vinculacion(this.colaborador, persona, LocalDateTime.now(), accesoAHeladeras);
        Vinculacion nuevaVinculacion = new Vinculacion(this.colaborador, persona, LocalDateTime.now());
        tarjetasRepartidas.add(nuevaVinculacion);
    }

    public void entregarTarjeta() {
        //
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
        this.totalDeDinero += monto;
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
        heladeraACargo.setColaborador(colaborador);//poe ahí hace falta un cast
    }

    public double puntosQueSumaColaborador() {
        // no por ahora
    }
}

class OfertaDeProductos extends Contribucion {
    String nombreOferta;
    Rubro rubro;
    double puntosNecesarios;

    public String getNombreOferta() {
        return nombreOferta;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public String getImagen() {
        return imagen;
    }

    public Producto getProducto() {
        return producto;
    }

    public OfertaDeProductos(String nombreOferta, Rubro rubro, double puntosNecesarios, String imagen, Producto producto) {
        this.nombreOferta = nombreOferta;
        this.rubro = rubro;
        this.puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
        this.producto = producto;
    }

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

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getStock() {
        return stock;
    }

    public Producto(String nombreProducto, int stock) {
        this.nombreProducto = nombreProducto;
        this.stock = stock;
    }


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

    public Imagen(String nombre, String resolucion, String epigrafe) {
        this.nombre = nombre;
        this.resolucion = resolucion;
        this.epigrafe = epigrafe;
    }

    public String getNombre() {
        return nombre;
    }

    public String getResolucion() {
        return resolucion;
    }

    public String getEpigrafe() {
        return epigrafe;
    }

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