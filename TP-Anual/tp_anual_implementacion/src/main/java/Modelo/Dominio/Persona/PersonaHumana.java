package Modelo.Dominio.Persona;

import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Persona_Humana")
public class PersonaHumana extends Persona {
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "fecha_de_nacimiento")
    private LocalDate fechaDeNacimiento;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "documento", referencedColumnName = "id_documento")
    private Documento documento;

    //Constructores -------------------------------------------------------------------------------------------------------------
    public PersonaHumana(String nombre, String apellido, LocalDate fechaDeNacimiento, Documento documento, Direccion direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.documento = documento;
        this.direccion = direccion;
    }

    public PersonaHumana() {

    }

    // Getters y Setters -------------------------------------------------------------------------------------------------------
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {this.apellido = apellido;}

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }
    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Documento getDocumento() {return documento;}
    public void setDocumento(Documento documento) {this.documento = documento;}
}
