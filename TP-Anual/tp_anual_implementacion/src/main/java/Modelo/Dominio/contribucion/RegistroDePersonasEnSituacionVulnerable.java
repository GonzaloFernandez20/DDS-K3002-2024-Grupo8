package Modelo.Dominio.contribucion;

import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroDePersonasEnSituacionVulnerable extends Contribucion{

    private List<PersonaSituacionVulnerable> personasEnSituacionVulnerableARegistrar; // ---------- Volaria
    private PersonaSituacionVulnerable personaARegistrar; // ---------- Volaria

    private List<Vinculacion> tarjetasRepartidas = new ArrayList<>(); // El tipo de dato lo infiere si ya esta definido en la declaracion de la variable
    private int cantidadSolicitada;

    @Override
    public void procesarLaContribucion() {
        if(colaborador.getPersona().getDireccion() != null) {
            personasEnSituacionVulnerableARegistrar.forEach(personaVulnerable -> this.generarRegistro(personaVulnerable));
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
    //public Object getHeladeraDeOrigen() { return null; }
    public List<Vinculacion> getTarjetasRepartidas() { return tarjetasRepartidas; }
}
