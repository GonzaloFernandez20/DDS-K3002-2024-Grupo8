package contribucion;

import colaborador.Colaborador;
import heladera.Heladera;
import persona_vulnerable.AccesoAHeladeras;
import persona_vulnerable.PersonaSituacionVulnerable;
import persona_vulnerable.Vinculacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import nuestras_excepciones.ColaboracionInvalida;

public class RegistroDePersonasEnSituacionVulnerable extends Contribucion{

    private List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar; // ---------- Volaria
    private PersonaSituacionVulnerable personaARegistrar; // ---------- Volaria

    private List<Vinculacion> tarjetasRepartidas = new ArrayList<>(); // El tipo de dato lo infiere si ya esta definido en la declaracion de la variable
    private int cantidadSolicitada;

    @Override
    public void procesarContribucion() throws ColaboracionInvalida{
        if(colaborador.getPersona().getDireccion() != null) {
            personasEnSituacionVulnerableARegistrar.forEach(personaVulnerable -> this.generarRegistro(personaVulnerable));
        }
         else{
             throw new ColaboracionInvalida("El colaborador que registre a múltiples vulnerables debe tener DIRECCION");
        }
    }

    @Override
    public double puntosQueSumaColaborador(){ return 0; }

    public void RegistrarVinculacion(Vinculacion nuevaVinculacion){ //TODO
    }

    // ------------------------------------------

    public RegistroDePersonasEnSituacionVulnerable(Colaborador colaborador, LocalDate fechaDeContribucion, List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar) {
        super(colaborador,fechaDeContribucion);
        if(personasEnSituacionVulnerableARegistrar != null) {
            this.personasEnSituacionVulnerableARegistrar = personasEnSituacionVulnerableARegistrar;
        } else {
            this.personasEnSituacionVulnerableARegistrar = new ArrayList<>();
        }
    }


    public void sumarPersonaEnSituacionVulnerableARegistrar(PersonaSituacionVulnerable persona) {
        personasEnSituacionVulnerableARegistrar.add(persona);
    }


    public void generarRegistro(PersonaSituacionVulnerable personaSituacionVulnerable, AccesoAHeladeras acceso) {
        Vinculacion vinculacion = new Vinculacion(colaborador, personaSituacionVulnerable, acceso);
        entregarTarjeta(vinculacion);
        //FALTA AGREGAR LA VINCULACION A LA LISTA DE VINCULACIONES DEL SISTEMA
    }

    public void entregarTarjeta(Vinculacion nuevaVinculacion) {
        tarjetasRepartidas.add(nuevaVinculacion);
    }
    @Override
    public Heladera getHeladera() { return null; }
    public boolean requieroAcceso() { return false;}
    //public Object getHeladera() { return null; }
    public List<Vinculacion> getTarjetasRepartidas() { return tarjetasRepartidas; }
}
