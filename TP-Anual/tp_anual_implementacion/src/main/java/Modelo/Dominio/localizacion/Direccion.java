package Modelo.Dominio.localizacion;

import jakarta.persistence.*;

@Entity
@Table(name = "Localizacion")
public class Direccion {
    @Id
    @GeneratedValue
    private Integer id_localizacion;
    @Column(name ="calle")
    private String calle;
    @Column(name ="altura")
    private String altura;
    @Column(name ="codPostal")
    private String codPostal;

    public Direccion(String calle, String altura, String codPostal) {
        if(calle ==null){throw new IllegalArgumentException("Una direccion necesita la calle");}
        if(altura == null){throw new IllegalArgumentException("Una direccion necesita la altura");}
        this.calle = calle;
        this.altura = altura;
        this.codPostal = codPostal;
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
    public String getCodPostal() {
        return codPostal;
    }
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
}