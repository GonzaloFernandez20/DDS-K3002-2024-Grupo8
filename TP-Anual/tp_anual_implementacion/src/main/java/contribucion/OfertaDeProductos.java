package contribucion;

import colaborador.Colaborador;
import sistema.Sistema;

import java.time.LocalDate;

public class OfertaDeProductos extends Contribucion {
    String nombreOferta;
    Rubro rubro;
    double puntosNecesarios;
    String imagen;
    Producto producto;

    public String getNombreOferta() {
        return nombreOferta;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean requieroAcceso() { return false;}

    public Producto getProducto() {
        return producto;
    }

    public OfertaDeProductos(Colaborador colaborador, LocalDate fechaDeDonacion, String nombreOferta, Rubro rubro, double puntosNecesarios, String imagen, Producto producto) {
        super(colaborador, fechaDeDonacion);
        this.nombreOferta = nombreOferta;
        this.rubro = rubro;
        this.puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
        this.producto = producto;
    }

    public void procesarContribucion(){
            Sistema.getInstancia().agregarOferta(this);
    }

    public double getPuntosNecesarios() { return puntosNecesarios; }

    public void disminuirStock() { producto.disminuirStock(); }

    public Object getHeladera() { return  null; }
}