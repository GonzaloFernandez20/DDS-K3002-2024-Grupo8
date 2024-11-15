package Repositorios;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.incidentes.Alerta;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.incidentes.TipoAlerta.FRAUDE;
import static Modelo.Dominio.incidentes.TipoAlerta.TEMPERATURA;

public class RepositorioIncidentes {
    private static RepositorioIncidentes instancia;
    private List<Incidente> incidentes;

    /*public RepositorioIncidentes() {
        incidentes = new ArrayList<>();

        // HARDCODEADO
        /*incidentes.add(new Alerta(FRAUDE, new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now())));
        incidentes.add(new Alerta(TEMPERATURA, new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 2"), 15, new Modelo(15, -2), LocalDate.now())));
    }*/

    public static RepositorioIncidentes getInstancia(){
        if(instancia == null){
            instancia = new RepositorioIncidentes();
        }
        return instancia;
    }

    public List<Incidente> getIncidentes() {
        verificarExistenciaDeIncidentes();
        return incidentes;
    }

    public void sumarIncidente(Incidente incidente){
        verificarExistenciaDeIncidentes();
        incidentes.add(incidente);
        System.out.println("Agregue " + incidente.obtenerInformacion());
    }

    private void verificarExistenciaDeIncidentes(){
        if(incidentes == null) {
            incidentes = new ArrayList<>();
        }
    }

    public void limpiarInstancia() {
        instancia = null;
    }

    public List<FallaTecnica> getFallasTecnicas(){
        verificarExistenciaDeIncidentes();
        return incidentes.stream().filter(incidente -> incidente instanceof FallaTecnica).map(incidente -> (FallaTecnica) incidente).toList();
    }

    public List<Alerta> getAlertas() {
        verificarExistenciaDeIncidentes();
        return incidentes.stream().filter(incidente -> incidente instanceof Alerta).map(incidente -> (Alerta) incidente).toList();
    }

    public List<FallaTecnica> getFallasTecnicasDeHeladeraEntreFechas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
        return getFallasTecnicas().stream().filter( falla -> falla.getHeladeraDondeOcurrio().equals(heladera) && falla.sucedioEntre(fechaInicio, fechaFin)).toList();
    }
}
