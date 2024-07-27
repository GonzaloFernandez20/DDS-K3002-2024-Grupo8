package contribucion;

import colaborador.Colaborador;
import persona_vulnerable.PersonaSituacionVulnerable;
import persona_vulnerable.Vinculacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import nuestras_excepciones.ColaboracionInvalida;

public class RegistroDePersonasEnSituacionVulnerable extends Contribucion{
    private List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar;
    private List<Vinculacion> tarjetasRepartidas = new ArrayList<Vinculacion>();
    private PersonaSituacionVulnerable personaARegistrar;

    public List<Vinculacion> getTarjetasRepartidas() {
        return tarjetasRepartidas;
    }

    public RegistroDePersonasEnSituacionVulnerable(Colaborador colaborador, LocalDate fechaDeContribucion, List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar) {
        super(colaborador,fechaDeContribucion);
        if(personasEnSituacionVulnerableARegistrar != null) {
            this.personasEnSituacionVulnerableARegistrar = personasEnSituacionVulnerableARegistrar;
        } else {
            this.personasEnSituacionVulnerableARegistrar = new ArrayList<>();
        }
    }

    public boolean requieroAcceso() { return false;}

    public void sumarPersonaEnSituacionVulnerableARegistrar(PersonaSituacionVulnerable persona) {
        personasEnSituacionVulnerableARegistrar.add(persona);
    }

    @Override
    public void procesarContribucion() throws ColaboracionInvalida{
        if(colaborador.getPersona().getDireccion() != null) {
            personasEnSituacionVulnerableARegistrar.forEach(personaVulnerable -> this.generarRegistro(personaVulnerable));
        }
         else{
             throw new ColaboracionInvalida("El colaborador que registre a múltiples vulnerables debe tener DIRECCION");
        }
    }

    public void generarRegistro(PersonaSituacionVulnerable personaSituacionVulnerable) {
        Vinculacion vinculacion = new Vinculacion(colaborador, personaSituacionVulnerable, LocalDate.now());
        entregarTarjeta(vinculacion);
        //FALTA AGREGAR LA VINCULACION A LA LISTA DE VINCULACIONES DEL SISTEMA
    }

    public void entregarTarjeta(Vinculacion nuevaVinculacion) {
        tarjetasRepartidas.add(nuevaVinculacion);
    }

    public Object getHeladera() { return null; }
}
