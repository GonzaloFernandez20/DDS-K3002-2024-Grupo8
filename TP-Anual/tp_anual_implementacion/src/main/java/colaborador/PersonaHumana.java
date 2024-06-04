package colaborador;

import heladera.Direccion;
import java.util.Date;

public class PersonaHumana extends Colaborador{
    String nombre;
    String apellido;
    Date fechaDeNacimiento;

    public PersonaHumana(String nombre, String apellido, Date fechaDeNacimiento, Direccion direccion) {
        super(direccion);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}
