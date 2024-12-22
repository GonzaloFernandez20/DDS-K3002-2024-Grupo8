package DTOs;

import Utils.DescargaDeArchivo;
import Modelo.Dominio.contribucion.Rubro;
import org.springframework.web.multipart.MultipartFile;

public class OfertaDeUnProductoDTO {
    private int idOferta;
    private String nombreOferta;
    private Integer puntosNecesarios;
    private String linkDeImagen;
    private String rubro;
    private String nombreProducto;
    private Integer stock;

    public OfertaDeUnProductoDTO(String nombreOferta, Integer puntosNecesarios, String path, Rubro rubro, String nombreProducto, Integer stock) {
        this.nombreOferta = nombreOferta;
        this.puntosNecesarios = puntosNecesarios;
        this.linkDeImagen = path;
        this.rubro = rubro.toString();
        this.nombreProducto = nombreProducto;
        this.stock = stock;
    }

    public int getIdOferta() { return idOferta; }

    public void setIdOferta(Integer idOferta){ this.idOferta = idOferta; }

    public void setNombreOferta(String nombreOferta) { this.nombreOferta = nombreOferta; }

    public String getNombreOferta() { return nombreOferta; }

    public void setPuntosNecesarios(Integer puntosNecesarios) { this.puntosNecesarios = puntosNecesarios;}

    public Integer getPuntosNecesarios() { return puntosNecesarios; }

    public void setLinkDeImagenAPartirDeArchivo(MultipartFile imagen) { this.linkDeImagen = DescargaDeArchivo.guardarArchivo("img/fotosProductosOServicios/", imagen); }

    public void setLinkDeImagenAPartirDePath(String path) { this.linkDeImagen = path; }

    public String getLinkDeImagen() { return linkDeImagen; }

    public void setRubro(Rubro rubro) { this.rubro = rubro.toString(); }

    public String getRubro() { return rubro; }

    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getNombreProducto() { return nombreProducto; }

    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStock() { return stock; }
}
