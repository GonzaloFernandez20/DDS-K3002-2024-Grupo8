package Modelo.Dominio.localizacion;

import jakarta.persistence.*;

@Entity
@Table(name = "Direccion")
public class Direccion {
    @Id
    @GeneratedValue
    private Integer id_direccion;
    @Column(name ="calle")
    private String calle;
    @Column(name ="altura")
    private String altura;

    public Direccion(String calle, String altura) {
        if(calle ==null){throw new IllegalArgumentException("Una direccion necesita la calle");}
        if(altura == null){throw new IllegalArgumentException("Una direccion necesita la altura");}
        this.calle = calle;
        this.altura = altura;
    }

    public Direccion() {
    }

    public String toString(){
        return calle+" "+altura;
    }

    // ----------> Getters y Setters
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getAltura() {
        return altura;
    }
    public void setAltura(String altura) {
        this.altura = altura;
    }
}