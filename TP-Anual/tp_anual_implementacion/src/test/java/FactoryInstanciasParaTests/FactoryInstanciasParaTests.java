package FactoryInstanciasParaTests;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
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
import Modelo.Dominio.persona_vulnerable.EstadoDeVivienda;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;

import java.time.LocalDate;
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
                                        10,
                                        new Modelo(5f,1f),
                                        null);
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);

        return heladera;
    }

    public static PersonaHumana instanciarPersonaHumana(){
        return new PersonaHumana(
                "Juan",
                "Carlos",
                null,
                new Documento(TipoDeDocumento.DNI, "12345678", null),
                null);
    }


    public static Colaborador instanciarColaboradorJuridico(){
        List<MedioDeContacto> medios = List.of(new Mail("textilesecologicos@gmail.com"));
        return new Colaborador(new PersonaJuridica("Textiles ecológicos S.A.",
                                                   TipoOrganizacion.EMPRESA,
                                                   "Textil",
                                                   null), medios);
    }

    public static Colaborador instanciarColaboradorHumano(){
        List<MedioDeContacto> medios = List.of(new Mail("juanCarlos@gmail.com"));
        return new Colaborador(instanciarPersonaHumana(), medios);
    }

    public static Vianda instanciarVianda(String tipo, LocalDate fechaCaducidad){
        return new Vianda(tipo,
                            fechaCaducidad,
                            instanciarColaboradorHumano(),
                            instanciarUnaHeladera(),
                            null,
                            null);
    }

    public static PersonaSituacionVulnerable instanciarPersonaEnSV(){
        PersonaSituacionVulnerable persona = new PersonaSituacionVulnerable(EstadoDeVivienda.SITUACION_DE_CALLE,
                                              2,
                                              null,
                                              instanciarPersonaHumana());
        Vinculacion vinculacion = new Vinculacion("AG780EX", persona, instanciarColaboradorHumano() );
        persona.setVinculacion(vinculacion);
        return persona;
    }
}

