package Modelo.Dominio.persona;
import Modelo.Dominio.localizacion.Direccion;

public abstract class Persona {
    public Direccion direccion;

    // ---- Getters y Setters
    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}