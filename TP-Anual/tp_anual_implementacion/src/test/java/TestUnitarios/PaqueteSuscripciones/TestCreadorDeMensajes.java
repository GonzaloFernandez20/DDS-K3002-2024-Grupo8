package TestUnitarios.PaqueteSuscripciones;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.Telegram;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.suscripcion.CreadorDeMensajes;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Pruebas relacionadas a la creacion de mensajes para notificaciones: ")
public class TestCreadorDeMensajes {
    Heladera heladera;
    Colaborador colaborador;

    // <---------------------- Configuraciones ----------------------> //
    @BeforeEach
    public void configuracionInicial(){
        inicializarHeladera();
        inicializarColaborador();

    }

    private void inicializarHeladera(){
        heladera = new Heladera(null,
                new Ubicacion(new Direccion("Medrano", "981", null), "CABA", "Heladera Medrano UTN"),
                5,
                null,
                null);
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);
    }

    private void inicializarColaborador(){
        PersonaHumana persona = new PersonaHumana("Juan", "Pérez",null , null, null);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        mediosDeContacto.add(new Mail("juan.perez@gmail.com"));
        mediosDeContacto.add(new Telegram( "1132283796"));

        colaborador = new Colaborador(persona, mediosDeContacto);
    }

    // <---------------------- Testeos ----------------------> //

    @Nested
    @DisplayName("Mensajes creados ante un evento relacionado a viandas")
    public class TestsEventosDeViandas{

        @Test
        @DisplayName("Se creo el mensaje ante el evento: quedan n viandas")
        public void testQuedanNViandas(){
            String mensaje = CreadorDeMensajes.crearMensaje("quedan 5 viandas", heladera);
            assertEquals("En la heladera: Heladera Medrano UTN (Medrano 981) " + "quedan 5 viandas", mensaje);
        }
    }

    @Nested
    @DisplayName("Mensajes creados ante un evento relacionado a fallas en heladeras")
    public class TestsEventosDeFallasDeHeladera {

        @Test
        @DisplayName("Se creo el mensaje ante el evento: se produjo una falla, pero no sugirio heladeras ya que no habian")
        public void testQuedanNViandas(){
            String mensaje = CreadorDeMensajes.crearMensaje("se produjo una falla.\n", heladera);
            assertEquals("En la heladera: Heladera Medrano UTN (Medrano 981) se produjo una falla.\n" +
                    "No hay heladeras disponibles para redistribucion.", mensaje);
        }
    }

    //TODO: Un test relacionado a devolver heladeras sugeridas
}
