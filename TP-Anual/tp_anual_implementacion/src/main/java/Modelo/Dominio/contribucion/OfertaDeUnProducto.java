package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;

import java.time.LocalDate;

public class OfertaDeUnProducto extends Contribucion {
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

}