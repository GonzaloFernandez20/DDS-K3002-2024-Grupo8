package contribucion;

import colaborador.Colaborador;
import persona.PersonaHumana;
import persona_vulnerable.EstadoDeVivienda;
import persona_vulnerable.PersonaSituacionVulnerable;
import persona_vulnerable.Vinculacion;


import java.util.List;
import java.time.LocalDate;

public class RegistroDePersonasEnSituacionVulnerable extends Contribucion {
    private List<Vinculacion> tarjetasRepartidas;

    public RegistroDePersonasEnSituacionVulnerable(Colaborador colaborador, LocalDate fechaDeDonacion, List<Vinculacion> tarjetasRepartidas) {
        super(colaborador, fechaDeDonacion);
        this.tarjetasRepartidas = tarjetasRepartidas;
    }
    public PersonaSituacionVulnerable pedirDatosPersonaARegistrar(){
        PersonaHumana persona = new PersonaHumana("Francisco","Perez",null,null,null);
        PersonaSituacionVulnerable personaDePrueba = new PersonaSituacionVulnerable(persona, EstadoDeVivienda.poseeDomicilio,2);
        return personaDePrueba;
    }
    @Override
    public void procesarContribucion() {
        if(colaborador.getPersona() instanceof PersonaHumana) {
            PersonaSituacionVulnerable persona = this.pedirDatosPersonaARegistrar();
            this.darDeAlta(persona);
        }
        else{
            //contribucion invalida, no es persona humana
        }
    }

    public void darDeAlta(PersonaSituacionVulnerable personaSituacionVulnerable) {
        Vinculacion vinculacion = new Vinculacion(colaborador, personaSituacionVulnerable, LocalDate.now());
        entregarTarjeta(vinculacion);
    }

    public void entregarTarjeta(Vinculacion nuevaVinculacion) {
        tarjetasRepartidas.add(nuevaVinculacion);
    }
}
