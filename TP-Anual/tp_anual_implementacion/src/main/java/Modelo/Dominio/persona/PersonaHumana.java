package Modelo.Dominio.persona;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.documentacion.Documento;

import java.time.LocalDate;

public class PersonaHumana extends Persona {
    String nombre;
    String apellido;
    LocalDate fechaDeNacimiento;
    Documento documento;

    public PersonaHumana(String nombre, String apellido, LocalDate fechaDeNacimiento, Documento documento, Direccion direccion) {
        super(direccion);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

}