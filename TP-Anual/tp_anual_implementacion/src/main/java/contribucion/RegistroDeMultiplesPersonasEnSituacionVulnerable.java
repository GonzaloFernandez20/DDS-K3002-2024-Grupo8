package contribucion;

import colaborador.Colaborador;
import persona_vulnerable.PersonaSituacionVulnerable;

import java.time.LocalDate;
import java.util.List;

public class RegistroDeMultiplesPersonasEnSituacionVulnerable extends Contribucion{
    private List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar;

    public RegistroDeMultiplesPersonasEnSituacionVulnerable(Colaborador colaborador, LocalDate fechaDeContribucion, List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar) {
        super(colaborador,fechaDeContribucion);
        this.personasEnSituacionVulnerableARegistrar = personasEnSituacionVulnerableARegistrar;
    }
    public void sumarPersonasEnSituacionVulnerableARegistrar(PersonaSituacionVulnerable persona) {
        personasEnSituacionVulnerableARegistrar.add(persona);
    }
    @Override
    public void procesarContribucion() {
        personasEnSituacionVulnerableARegistrar.forEach(personaEnSituacionVulnerable ->{
        RegistroDePersonaEnSituacionVulnerable registro = new RegistroDePersonaEnSituacionVulnerable(colaborador,fechaDeDonacion,personaEnSituacionVulnerable);
        registro.procesarContribucion();});
    }
}
