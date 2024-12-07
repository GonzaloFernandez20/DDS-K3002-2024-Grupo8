package Modelo.Dominio.Persona;
import Modelo.Dominio.localizacion.Direccion;
import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona {
    @Id
    @GeneratedValue
    private Integer id_persona;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "direccion", referencedColumnName = "id_direccion")
    public Direccion direccion;

    // Getters y Setters -----------------------------------------------------------
    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}