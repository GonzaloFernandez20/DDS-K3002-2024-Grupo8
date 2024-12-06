package DTOs;

import Controladores.DescargaDeArchivo;
import Modelo.Dominio.contribucion.Rubro;
import org.springframework.web.multipart.MultipartFile;

public class OfertaDeUnProductoDTO {
    private int idOferta;
    private String nombreOferta;
    private double puntosNecesarios;
    private String pathImagen;
    private String rubro;
    private String nombreProducto;
    private int stock;

    public OfertaDeUnProductoDTO(String nombreOferta, double puntosNecesarios, String path, Rubro rubro, String nombreProducto, int stock) {
        this.nombreOferta = nombreOferta;
        this.puntosNecesarios = puntosNecesarios;
        this.pathImagen = path;
        this.rubro = rubro.toString();
        this.nombreProducto = nombreProducto;
        this.stock = stock;
    }

    public int getIdOferta() { return idOferta; }

    public void setNombreOferta(String nombreOferta) { this.nombreOferta = nombreOferta; }

    public String getNombreOferta() { return nombreOferta; }

    public void setPuntosNecesarios(double puntosNecesarios) { this.puntosNecesarios = puntosNecesarios;}

    public double getPuntosNecesarios() { return puntosNecesarios; }

    public void setPathImagenAPartirDeArchivo(MultipartFile imagen) { this.pathImagen = DescargaDeArchivo.guardarArchivo("/fotosProductosOServicios/", imagen); }

    public void setPathImagenAPartirDePath(String path) { this.pathImagen = path; }

    public String getPathImagen() { return pathImagen; }

    public void setRubro(Rubro rubro) { this.rubro = rubro.toString(); }

    public String getRubro() { return rubro; }

    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getNombreProducto() { return nombreProducto; }

    public void setStock(int stock) { this.stock = stock; }

    public int getStock() { return stock; }
}
