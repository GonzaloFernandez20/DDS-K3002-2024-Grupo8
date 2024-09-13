package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.documentacion.Documento;

import java.time.LocalDate;

public class PersonaHumana extends Persona {
    private final String nombre;
    private final String apellido;
    private LocalDate fechaDeNacimiento;
    private final Documento documento;

    public PersonaHumana(String nombre, String apellido, LocalDate fechaDeNacimiento, Documento documento, Direccion direccion) {
        if(nombre ==null){throw new IllegalArgumentException("El nombre es obligatorio");}
        if(apellido ==null){throw new IllegalArgumentException("El apellido es obligatorio");}
        if(documento ==null){throw new IllegalArgumentException("El documento es obligatorio");}
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
