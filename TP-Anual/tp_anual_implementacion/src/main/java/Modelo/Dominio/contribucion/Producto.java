package Modelo.Dominio.contribucion;

import jakarta.persistence.*;

@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue
    private Integer id_producto;
    @Column(name = "nombre_de_producto")
    String nombreProducto;
    @Column(name = "stock")
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

    public void disminuirStock() { this.stock--; }
    public boolean tengoStock() { return stock > 0; }
}
