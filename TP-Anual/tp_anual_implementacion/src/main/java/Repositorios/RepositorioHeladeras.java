package Repositorios;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras {

    private static RepositorioHeladeras instancia;
    private static List<Heladera> heladeras;

    public static RepositorioHeladeras getInstancia(){
        if(instancia == null){
            instancia = new RepositorioHeladeras();
        }
        return instancia;
    }

    public List<Heladera> getHeladeras() {
        /*return List.of(
                new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now()),
                new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 2"), 15, new Modelo(15, -2), LocalDate.now()),
                );*/
        verificarExistenciaDeHeladeras();
        heladeras.add(new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now()));
        heladeras.add(new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50", "1010"), "CABA", "Gastronomos Argentinos 2"), 15, new Modelo(15, -2), LocalDate.now()));
        return heladeras;
    }

    public Heladera buscarHeladeraPorId(int idBuscado) {
        return heladeras.stream().filter(heladera -> heladera.getIdHeladera() == idBuscado).findFirst().orElse(null);
    }

    public void agregarHeladera(Heladera heladera) {
        verificarExistenciaDeHeladeras();
        System.out.println("Agrego heladera " + heladera.getUbicacion().getNombreCompletoDeUbicacion());
        heladeras.add(heladera);
    }

    private void verificarExistenciaDeHeladeras() {
        if(heladeras == null){
            heladeras = new ArrayList<>();
        }
    }
}