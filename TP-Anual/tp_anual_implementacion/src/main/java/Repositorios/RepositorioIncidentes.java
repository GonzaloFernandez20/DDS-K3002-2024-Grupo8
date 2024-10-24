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
    // HARDCODEADO
    private final List<Incidente> incidentes = List.of(
            new Alerta(FRAUDE, new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now())),
            new Alerta(TEMPERATURA, new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 2"), 15, new Modelo(15, -2), LocalDate.now()))
    );

    public static RepositorioIncidentes getInstancia(){
        if(instancia == null){
            instancia = new RepositorioIncidentes();
        }
        return instancia;
    }

    public List<Incidente> getIncidentes() {return incidentes;}

    public void sumarIncidente(Incidente incidente){incidentes.add(incidente);}
    public List<Incidente> getFallasTecnicas(){
        return incidentes.stream().filter(incidente -> incidente instanceof FallaTecnica).toList();
    }
    public List<Alerta> getAlertas() {
        return incidentes.stream().filter(incidente -> incidente instanceof Alerta).map(incidente -> (Alerta) incidente).toList();
    }
}
