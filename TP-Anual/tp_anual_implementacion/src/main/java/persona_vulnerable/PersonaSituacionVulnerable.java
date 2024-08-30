package persona_vulnerable;

import Accesos_a_heladeras.Vinculacion;
import persona.Persona;
import persona.PersonaHumana;
import documentacion.Documento;
import localizacion.Direccion;

import java.time.LocalDate;

public class PersonaSituacionVulnerable extends PersonaHumana {
    private EstadoDeVivienda estadoDeVivienda;
    private int cantMenores;
    private Vinculacion vinculacion;
    private final PersonaHumana persona;

    public PersonaSituacionVulnerable(String nombre, String apellido,
                                      LocalDate fechaDeNacimiento,
                                      Documento documento,
                                      Direccion direccion,
                                      EstadoDeVivienda estadoDeVivienda,
                                      int cantMenores,
                                      Vinculacion vinculacion,
                                      PersonaHumana persona) {
        super(nombre, apellido, fechaDeNacimiento, documento, direccion);
        this.estadoDeVivienda = estadoDeVivienda;
        this.cantMenores = cantMenores;
        this.vinculacion = vinculacion;
        this.persona = persona;
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