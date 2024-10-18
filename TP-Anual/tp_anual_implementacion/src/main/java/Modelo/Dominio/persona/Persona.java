package Modelo.Dominio.persona;
import Modelo.Dominio.localizacion.Direccion;
import jakarta.persistence.*;

@Entity
@MappedSuperclass
public abstract class Persona {
    @Id
    @GeneratedValue
    private int id_persona;
    @OneToOne
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    public Direccion direccion;

    // ---- Getters y Setters
    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}