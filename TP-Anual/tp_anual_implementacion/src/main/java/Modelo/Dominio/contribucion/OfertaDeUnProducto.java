package Modelo.Dominio.contribucion;

import DTOs.OfertaDeUnProductoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Entity
@Table(name = "OfertaDeUnProducto")
@DiscriminatorValue("ofrecer_producto")
public class OfertaDeUnProducto extends Contribucion {
    //POR QUÉ ESTÁ ÉSTE ATRIBUTO???
    @Column(name = "idOferta")
    private int idOferta;
    @Column(name = "nombre_de_oferta")
    private String nombreOferta;
    @Column(name = "puntos_necesarios")
    private double puntosNecesarios;
    @Column(name = "imagen")
    private MultipartFile imagen;
    @Enumerated(EnumType.STRING)
    private Rubro rubro;
    @OneToOne
    @JoinColumn(name = "producto", referencedColumnName = "id_producto")
    private Producto producto;

    public OfertaDeUnProducto(Colaborador colaborador, String nombreOferta, double puntosNecesarios, MultipartFile imagen, Rubro rubro, Producto producto) {
        this.nombreOferta = nombreOferta;
        this.puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
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
    public MultipartFile getImagen() { return imagen; }
    public void setImagen(MultipartFile imagen) { this.imagen = imagen; }
    public Rubro getRubro() { return rubro; }
    public void setRubro(Rubro rubro) { this.rubro = rubro; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getIdOferta(){return this.idOferta;} // TODO: GENERAR UN CODIGO QUE SE ASIGNE LA PRIMERA VEZ QUE SE EJECUTE EL METODO (STRING)
}