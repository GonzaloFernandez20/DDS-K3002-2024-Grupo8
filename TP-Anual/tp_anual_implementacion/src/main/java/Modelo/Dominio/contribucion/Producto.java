package Modelo.Dominio.contribucion;

public class Producto {
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

    public void disminuirStock() { this.stock--; }
    public boolean tengoStock() { return stock > 0; }
}
