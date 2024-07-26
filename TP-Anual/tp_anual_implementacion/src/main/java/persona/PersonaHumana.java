package persona;

import colaborador.Colaborador;
import heladera.Heladera;
import localizacion.Direccion;
import documentacion.Documento;
import org.springframework.cglib.core.Local;
import suscripcion.GestorDeSuscripciones;
import suscripcion.Suscripcion;
import suscripcion.TipoSuscripcion;

import java.time.LocalDate;
import java.util.Date;

public class PersonaHumana extends Persona {
    String nombre;
    String apellido;
    LocalDate fechaDeNacimiento;

    public PersonaHumana(String nombre, String apellido, LocalDate fechaDeNacimiento, Documento documento, Direccion direccion) {
        super(direccion, documento);
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
