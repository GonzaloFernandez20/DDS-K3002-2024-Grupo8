package TestUnitarios.PaqueteBrokers;

import static org.junit.jupiter.api.Assertions.*;

import Modelo.Brokers.BrokerAccesoHeladeras;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.Persona;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrokerAccesoHeladerasTest {

    private BrokerAccesoHeladeras broker;
    private Heladera heladera1;
    private Heladera heladera2;

    @BeforeEach
    public void setUp() {
        broker = new BrokerAccesoHeladeras(8080);
        //broker.iniciarServidor();
        Direccion direccion = new Direccion("Medrano", "124", "1234");
        MedioDeContacto medioDeContacto = new MedioDeContacto() {
            @Override
            public void notificar(String mensaje) {
                System.out.print("soy un medio de contacto");
            }
        };
        Ubicacion ubicacion = new Ubicacion(direccion, "Buenos Aires", "ALmagro");
        Persona personaJuridica = new PersonaJuridica("ONG", TipoOrganizacion.ONG, "Gastronomia", direccion);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        mediosDeContacto.add(new Mail("gastroalmagro@gmail.com"));
        Colaborador colaborador = new Colaborador(personaJuridica, mediosDeContacto);
        Modelo modelo = new Modelo("Heladera3000", 15, 9);
        heladera1 = new Heladera(colaborador, ubicacion, 10, modelo, LocalDate.now());
        Direccion direccion2 = new Direccion("Independencia", "124", "1234");
        MedioDeContacto medioDeContacto2 = new MedioDeContacto() {
            @Override
            public void notificar(String mensaje) {
                System.out.print("soy un medio de contacto");
            }
        };
        Ubicacion ubicacion2 = new Ubicacion(direccion, "Buenos Aires", "ALmagro");
        Persona personaJuridica2 = new PersonaJuridica("ONG", TipoOrganizacion.ONG, "Gastronomia", direccion2);
        List<MedioDeContacto> mediosDeContacto2 = new ArrayList<>();
        mediosDeContacto2.add(new Mail("almagrogastro@gmail.com"));
        Colaborador colaborador2 = new Colaborador(personaJuridica, mediosDeContacto);
        Modelo modelo2 = new Modelo("Heladera3500", 15, 9);
        heladera2 = new Heladera(colaborador2, ubicacion2, 10, modelo2, LocalDate.now());
    }

    @Test
    public void testAgregarHeladerasAlDiccionario() {
        broker.agregarHeladerasAlDiccionario(1, heladera1);
        broker.agregarHeladerasAlDiccionario(2, heladera2);

        assertEquals(heladera1, broker.diccionarioHeladeras.get(1));
        assertEquals(heladera2, broker.diccionarioHeladeras.get(2));
    }

    @Test
    public void testQuitarHeladerasDelDiccionario() {
        broker.agregarHeladerasAlDiccionario(1, heladera1);
        broker.quitarHeladerasDelDiccionario(1);

        assertNull(broker.diccionarioHeladeras.get(1));
    }
}
