package contribucion;

import colaborador.Colaborador;
import persona.PersonaHumana;
import persona_vulnerable.PersonaSituacionVulnerable;

import java.time.LocalDate;
import java.util.List;
import nuestras_excepciones.ColaboracionInvalida;

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
    public void procesarContribucion() throws ColaboracionInvalida{
        if(colaborador.getPersona() instanceof PersonaHumana && colaborador.getPersona().getDireccion() != null)
        {
            personasEnSituacionVulnerableARegistrar.forEach(personaEnSituacionVulnerable ->
            {
                RegistroDePersonaEnSituacionVulnerable registro = new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaDeDonacion, personaEnSituacionVulnerable);
                try {
                    registro.procesarContribucion();
                } catch (ColaboracionInvalida e) {
                    throw new RuntimeException(e);
                }
            }   );
        }
         else{
             throw new ColaboracionInvalida("El colaborador que registre a múltiples vulnerables debe ser una persona HUMANA con DIRECCION");
        }
    }
}
