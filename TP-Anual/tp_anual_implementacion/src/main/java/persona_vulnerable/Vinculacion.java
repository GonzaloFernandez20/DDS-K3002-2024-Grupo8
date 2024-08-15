package persona_vulnerable;

import colaborador.Colaborador;

import java.time.LocalDate;

public class Vinculacion {
    Colaborador colaboradorQueRegistro;
    PersonaSituacionVulnerable personaSituacionVulnerable;
    LocalDate fechaRegistro;
    AccesoAHeladeras tarjetaEntregada;

    public Vinculacion(Colaborador colaborador, PersonaSituacionVulnerable personaSituacionVulnerable, AccesoAHeladeras acceso) {
        colaboradorQueRegistro = colaborador;
        fechaRegistro = LocalDate.now();
        this.vincular(personaSituacionVulnerable);
        tarjetaEntregada = acceso;
        this.personaSituacionVulnerable = personaSituacionVulnerable;
    }

    public Colaborador getColaboradorQueRegistro() {
        return colaboradorQueRegistro;
    }

    public PersonaSituacionVulnerable getPersonaSituacionVulnerable() {
        return personaSituacionVulnerable;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void vincular(PersonaSituacionVulnerable persona){
        persona.setVinculacion(this);
    }

    public void setTarjetaEntregada(AccesoAHeladeras tarjetaEntregada) { this.tarjetaEntregada = tarjetaEntregada; }
    public AccesoAHeladeras getTarjetaEntregada() {
        return tarjetaEntregada;
    }
}