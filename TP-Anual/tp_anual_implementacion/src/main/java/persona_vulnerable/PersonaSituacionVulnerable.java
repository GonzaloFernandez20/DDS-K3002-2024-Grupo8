package persona_vulnerable;

import persona.Persona;
import persona.PersonaHumana;
import documentacion.Documento;
import localizacion.Direccion;

import java.time.LocalDate;

public class PersonaSituacionVulnerable extends PersonaHumana {
    EstadoDeVivienda estadoDeVivienda;
    int cantMenores;
    Vinculacion vinculacion;
    PersonaHumana persona;

    public PersonaSituacionVulnerable(Persona persona,
                                      String nombre,
                                      String apellido,
                                      LocalDate fechaDeNacimiento,
                                      Documento documento,
                                      Direccion direccion,
                                      EstadoDeVivienda estado,
                                      int menoresAcargo){
        super(nombre, apellido, fechaDeNacimiento, documento, direccion);
        this.estadoDeVivienda = estado;
        this.cantMenores = menoresAcargo;
    }

    // ---- Getters y Setters
    public void setCantMenores(int cantMenores) {
        this.cantMenores = cantMenores;
    }
    public void setVinculacion(Vinculacion unaVinculacion){
        this.vinculacion = unaVinculacion;
    }
    public EstadoDeVivienda getEstadoDeVivienda() {
        return estadoDeVivienda;
    }
    public int getCantMenores() {
        return cantMenores;
    }
    public Vinculacion getVinculacion() {
        return vinculacion;
    }
}