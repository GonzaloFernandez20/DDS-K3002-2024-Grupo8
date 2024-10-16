package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;

import java.time.LocalDate;

public class OfertaDeUnProducto extends Contribucion {
    private int idOferta;
    private String nombreOferta;
    private double puntosNecesarios;
    private String linkDeImagen;
    private Rubro rubro;
    private Producto producto;

    public OfertaDeUnProducto(Colaborador colaborador, String nombreOferta, double puntosNecesarios, String linkDeImagen, Rubro rubro, Producto producto) {
        this.nombreOferta = nombreOferta;
        this.puntosNecesarios = puntosNecesarios;
        this.linkDeImagen = linkDeImagen;
        this.rubro = rubro;
        this.producto = producto;
        this.colaborador = colaborador;
        this.fechaDeContribucion = LocalDate.now();
    }

    @Override
    public void procesarLaContribucion() {
        colaborador.registrarContribucion(this);
        Sistema.getInstancia().agregarOferta(this);
    }

    @Override
    public double puntosQueSumaColaborador() { return 0; }

    public void serCanjeada() { producto.disminuirStock(); }
    public boolean hayStock() { return producto.tengoStock(); }

    // ---- Getters y Setters
    public double getPuntosNecesarios() { return puntosNecesarios; }
    public String getNombreOferta() { return nombreOferta; }
    public void setNombreOferta(String nombreOferta) { this.nombreOferta = nombreOferta; }
    public void setPuntosNecesarios(double puntosNecesarios) { this.puntosNecesarios = puntosNecesarios; }
    public String getLinkDeImagen() { return linkDeImagen; }
    public void setLinkDeImagen(String linkDeImagen) { this.linkDeImagen = linkDeImagen; }
    public Rubro getRubro() { return rubro; }
    public void setRubro(Rubro rubro) { this.rubro = rubro; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getIdOferta(){return this.idOferta;} // TODO: GENERAR UN CODIGO QUE SE ASIGNE LA PRIMERA VEZ QUE SE EJECUTE EL METODO (STRING)
}