package contribucion;

import java.time.LocalDate;

import colaborador.Colaborador;
import seguridad.Validador;

class GestorDeContribuciones{
    /*     comentario:
                    Este recibe ya instanciada una contribucion y se encarga de efectuar();
                    Se decide que al igual que un colaborador, su instanciacion depende de un form. */
    private static GestorDeContribuciones instancia = null;

    private GestorDeContribuciones(){ }
    public static GestorDeContribuciones getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeContribuciones();
        }
        return instancia;
    }

    private void realizarContribucion(Contribucion contribucion, Colaborador colaborador){
        contribucion.procesarLaContribucion();
        colaborador.sumarContribucion(contribucion);
    }
}

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaDeDonacion;

    public abstract void procesarLaContribucion();

    public abstract double puntosQueSumaColaborador();

    public Contribucion(Colaborador colaborador, LocalDate fechaDeDonacion) {
        this.colaborador = colaborador;
        this.fechaDeDonacion = fechaDeDonacion;
    }
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
    private String resolucion;
    private String epigrafe;

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
}

enum Frecuencia {
    UNICAMENTE,
    DIARIAMENTE,
    SEMANALMENTE,
    MENSUALMENTE,
    ANUALMENTE,
    PERSONALIZADO
}