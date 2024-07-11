package contribucion;

import java.time.LocalDate;

import colaborador.Colaborador;
import nuestras_excepciones.ColaboracionInvalida;

class GestorDeContribuciones{
    /*     comentario:
                    Este recibe ya instanciada una contribucion y se encarga de efectuar();
                    Se decide que al igual que un colaborador, su instanciacion depende de un form. */
    //estos atributos deberían ser pétreos porque la asociación entre colaborador y contribución no puede cambiar
    private final Contribucion contribucionActual;
    private final Colaborador colaboradorActual;

    //Inyector de dependencias ->constructor
    public GestorDeContribuciones(Contribucion contribucion, Colaborador colaborador){
        this.contribucionActual = contribucion;
        this.colaboradorActual = colaborador;
    }

    private void realizarContribucion(){
        try {
            contribucionActual.procesarContribucion();
            colaboradorActual.sumarContribucion(contribucionActual);
        }
        catch (ColaboracionInvalida e){
            e.printStackTrace();
            System.out.print("CONTRIBUCION ABORTADA");
            System.out.print(contribucionActual);
        }
    }
}

public abstract class Contribucion {
    protected Colaborador colaborador;
    protected LocalDate fechaDeDonacion;

    public abstract void procesarContribucion() throws ColaboracionInvalida;

    public double puntosQueSumaColaborador() {
        return 0;
    }

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