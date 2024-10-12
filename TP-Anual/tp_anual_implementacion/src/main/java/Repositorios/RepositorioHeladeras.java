package Repositorios;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;

import java.time.LocalDate;
import java.util.List;

public class RepositorioHeladeras {

    private static RepositorioHeladeras instancia;
    private static List<Heladera> heladeras = List.of(
            new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now()),
            new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 2"), 15, new Modelo(15, -2), LocalDate.now())
    );

    public static RepositorioHeladeras getInstancia(){
        if(instancia == null){
            instancia = new RepositorioHeladeras();
        }
        // HARDCODEADO HASTA LA PERSISTENCIA
        /*
        Direccion direccion = new Direccion("Perú", "50", "1010");
        Modelo modelo = new Modelo("Heladera3000", 15, -2);
        Colaborador colaborador = new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", direccion), List.of(new Mail("gastronomosargentinos@gmail.com")));
        Heladera heladera1 = new Heladera(colaborador, new Ubicacion(direccion, "CABA", "Gastronomos Argentinos 1"), 15, modelo, LocalDate.now());
        Heladera heladera2 = new Heladera(colaborador, new Ubicacion(direccion, "CABA", "Gastronomos Argentinos 2"), 15, modelo, LocalDate.now());
        heladeras.add(heladera1);
        heladeras.add(heladera2);
         */
        ////////////////////////////////////
        return instancia;
    }

    public List<Heladera> getHeladeras() { return heladeras;}

    public Heladera buscarHeladeraPorId(int idBuscado) {
        return heladeras.stream().filter(heladera -> heladera.getIdHeladera() == idBuscado).findFirst().orElse(null);
    }
}