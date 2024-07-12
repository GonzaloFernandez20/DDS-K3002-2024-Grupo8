package persona;

import colaborador.Colaborador;
import localizacion.Direccion;
import documentacion.Documento;

import java.util.Date;

public class PersonaHumana extends Persona {
    String nombre;
    String apellido;
    Date fechaDeNacimiento;
    Documento documento;

    public PersonaHumana(String nombre, String apellido, Date fechaDeNacimiento,Documento documento, Direccion direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.documento = documento;
        this.direccion = direccion;
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

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

}
