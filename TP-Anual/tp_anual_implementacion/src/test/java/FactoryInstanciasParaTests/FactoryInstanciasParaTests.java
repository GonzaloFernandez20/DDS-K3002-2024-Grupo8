package FactoryInstanciasParaTests;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class FactoryInstanciasParaTests {
    public static Heladera instanciarUnaHeladera(){
        Heladera heladera = new Heladera(instanciarColaboradorJuridico(),
                                         new Ubicacion(new Direccion("Mozart", "2300", null), "CABA", "Heladera Campus UTN"),
                                        3,
                                        new Modelo(5f,1f),
                                        null);
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);

        return heladera;
    }
    public static Heladera instanciarOtraHeladera(){
        Heladera heladera = new Heladera(instanciarColaboradorJuridico(),
                                         new Ubicacion(new Direccion("Medrano", "981", null), "CABA", "Heladera Medrano UTN"),
                                        3,
                                        new Modelo(5f,1f),
                                        null);
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);

        return heladera;
    }
    public static Colaborador instanciarColaboradorJuridico(){
        List<MedioDeContacto> medios = Arrays.asList(new Mail("textilesecologicos@gmail.com"));
        Colaborador colaborador = new Colaborador(new PersonaJuridica(
                                                                     "Textiles ecológicos S.A.",
                                                                     TipoOrganizacion.EMPRESA,
                                                                     "Textil",
                                                                     null), medios);
        return colaborador;
    }

    public static Colaborador instanciarColaboradorHumano(){
        List<MedioDeContacto> medios = Arrays.asList(new Mail("juanCarlos@gmail.com"));
        Colaborador colaborador = new Colaborador(new PersonaHumana(
                                                                "Juan",
                                                                "Carlos",
                                                                null,
                                                                new Documento(TipoDeDocumento.DNI, "12345678", null),
                                                                null), medios);
        return colaborador;
    }

    public static Vianda instanciarVianda(String tipo, LocalDate fechaCaducidad){
        Vianda vianda = new Vianda(tipo,
                                    fechaCaducidad,
                                    instanciarColaboradorHumano(),
                                    instanciarUnaHeladera(),
                                    null,
                                    null);
        return vianda;
    }
}

