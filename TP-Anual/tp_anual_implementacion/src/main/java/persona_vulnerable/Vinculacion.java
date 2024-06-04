package persona_vulnerable;

import colaborador.Colaborador;
import colaborador.PersonaHumana;

import java.time.LocalDate;

public class Vinculacion {
    PersonaHumana colaboradorQueRegistro;
    PersonaSituacionVulnerable personaSituacionVulnerable;
    LocalDate fechaRegistro;
    AccesoAHeladeras tarjetaEntregada;

    public Vinculacion(Colaborador colaborador, PersonaSituacionVulnerable personaSituacionVulnerable, LocalDate fecha) {
        colaboradorQueRegistro = (PersonaHumana) colaborador;
        fechaRegistro = fecha;
        this.vincular(personaSituacionVulnerable);
        tarjetaEntregada = new AccesoAHeladeras(personaSituacionVulnerable);
    }

    public void vincular(PersonaSituacionVulnerable persona){
        persona.setVinculacion(this);
    }

    public void setTarjetaEntregada(AccesoAHeladeras tarjetaEntregada) { this.tarjetaEntregada = tarjetaEntregada; }
    public AccesoAHeladeras getTarjetaEntregada() {
        return tarjetaEntregada;
    }
}