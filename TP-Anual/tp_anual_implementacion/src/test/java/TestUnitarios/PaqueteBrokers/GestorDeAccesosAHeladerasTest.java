package TestUnitarios.PaqueteBrokers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import Modelo.Brokers.BrokerAccesoHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Accesos_a_heladeras.GestorDeAccesosAHeladeras;
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
import Modelo.Dominio.sensoreos.LectorDeTarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorDeAccesosAHeladerasTest {

    private GestorDeAccesosAHeladeras gestor;
    private Heladera heladera;
    private AccesoAHeladeras accesoAutorizado;
    private AccesoAHeladeras accesoNoAutorizado;
    private BrokerAccesoHeladeras broker;

    @BeforeEach
    public void setUp() {
        BrokerAccesoHeladeras broker = new BrokerAccesoHeladeras(3456);
        //broker.iniciarServidor();
        gestor = GestorDeAccesosAHeladeras.getInstancia();
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
        heladera = new Heladera(colaborador, ubicacion, 10, modelo, LocalDate.now());
        // el lector envia el id junto con un codigo de tarjeta
        LectorDeTarjeta lectorDeTarjeta = new LectorDeTarjeta(1);
        lectorDeTarjeta.enviarDatos("localhost", 3456);
        // ahora esta puesto para que genere el codigo permitido.

        accesoAutorizado = new AccesoDeColaborador("12345678", colaborador);
        accesoNoAutorizado = new AccesoDeColaborador("87654321", colaborador);

        gestor.tarjetasRegistradas.add(accesoAutorizado);

        broker.agregarHeladerasAlDiccionario(1, heladera);
    }

    @Test
    public void testAutorizarAperturaConTarjetaRegistrada() {
        assertEquals("Acceso permitido", broker.getRespuesta(), "El acceso fue permitido");
    }

    @Test
    public void testAutorizarAperturaConTarjetaNoRegistrada() {
        assertEquals("Acceso denegado", broker.getRespuesta(), "El acceso fue denegado");
    }
}
