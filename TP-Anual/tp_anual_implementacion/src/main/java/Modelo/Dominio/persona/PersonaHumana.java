package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.documentacion.Documento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "PersonaHumana")
public class PersonaHumana extends Persona {
    @Column(name = "nombre")
    private final String nombre;
    @Column(name = "apellido")
    private final String apellido;
    @Column(name = "fecha_de_nacimiento")
    private LocalDate fechaDeNacimiento;
    @OneToOne
    @JoinColumn(name = "id_documento", referencedColumnName = "id_documento")
    private final Documento documento;

    public PersonaHumana(String nombre, String apellido, LocalDate fechaDeNacimiento, Documento documento, Direccion direccion) {
        if(nombre ==null){throw new IllegalArgumentException("El nombre es obligatorio");}
        if(apellido ==null){throw new IllegalArgumentException("El apellido es obligatorio");}
        // if(documento ==null){throw new IllegalArgumentException("El documento es obligatorio");}
        // EL DOCUMENTO NO ES OBLIGATORIO. LAS PERSONAS EN SITUACIÓN VULNERABLE PUEDEN NO TENER DOCUMENTO.
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.documento = documento;
        this.direccion = direccion;
    }

    // ---- Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }
    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
    public Documento getDocumento() {
        return documento;
    }
}
