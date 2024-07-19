package persona_vulnerable;

import colaborador.Colaborador;
import persona.Persona;
import persona.PersonaHumana;
import documentacion.Documento;
import localizacion.Direccion;
import heladera.Heladera;
import heladera.Vianda;
import sistema.Sistema;

import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class PersonaSituacionVulnerable {
    EstadoDeVivienda estadoDeVivienda;
    int cantMenores;
    Vinculacion vinculacion;
    PersonaHumana persona;

    public PersonaSituacionVulnerable(PersonaHumana persona, EstadoDeVivienda estado, int menores){
        this.estadoDeVivienda = estado;
        this.cantMenores = menores;
        this.persona = persona;
    }

    public void setCantMenores(int cantMenores) {
        this.cantMenores = cantMenores;
    }
    public void setVinculacion(Vinculacion unaVinculacion){
        this.vinculacion = unaVinculacion;
    }

    public Direccion getDomicilio() {
        return persona.getDireccion();
    }

    public EstadoDeVivienda getEstadoDeVivienda() {
        return estadoDeVivienda;
    }

    public int getCantMenores() {
        return cantMenores;
    }

    public LocalDate getFechaDeNacimiento() { return persona.getFechaDeNacimiento(); }

    public Documento getDocumento() {
        return persona.getDocumento();
    }

    public String getNombre() {
        return persona.getNombre();
    }

    public Vinculacion getVinculacion() {
        return vinculacion;
    }
}

