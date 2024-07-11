package contribucion;

import colaborador.Colaborador;
import persona.PersonaHumana;
import persona_vulnerable.EstadoDeVivienda;
import persona_vulnerable.PersonaSituacionVulnerable;
import persona_vulnerable.Vinculacion;
import nuestras_excepciones.ColaboracionInvalida;

import java.util.List;
import java.time.LocalDate;

public class RegistroDePersonaEnSituacionVulnerable extends Contribucion {
    private Vinculacion tarjetaRepartidas;
    private PersonaSituacionVulnerable personaARegistrar;

    public RegistroDePersonaEnSituacionVulnerable(Colaborador colaborador, LocalDate fechaDeDonacion,PersonaSituacionVulnerable personaARegistrar) {
        super(colaborador, fechaDeDonacion);
        this.tarjetaRepartidas = null;
        this.personaARegistrar = personaARegistrar;
    }
    @Override
    public void procesarContribucion() throws ColaboracionInvalida {
        if(colaborador.getPersona() instanceof PersonaHumana && colaborador.getDireccion() != null) {
            this.darDeAlta(personaARegistrar);
        }
        else{
            //contribucion invalida, no es persona humana
            throw new ColaboracionInvalida("El registro de vulnerable debe ser hecho por una persona HUMANA con DIRECCION");
        }
    }

    public void darDeAlta(PersonaSituacionVulnerable personaSituacionVulnerable) {
        Vinculacion vinculacion = new Vinculacion(colaborador, personaSituacionVulnerable, LocalDate.now());
        entregarTarjeta(vinculacion);
    }

    public void entregarTarjeta(Vinculacion nuevaVinculacion) {
        tarjetaRepartidas = nuevaVinculacion;
    }
}
