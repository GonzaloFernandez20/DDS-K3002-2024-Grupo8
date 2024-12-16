package TestUnitarios.PaqueteSuscripciones;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.suscripcion.CreadorDeMensajes;
import org.junit.jupiter.api.*;

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
        heladera = FactoryInstanciasParaTests.instanciarOtraHeladera();
    }

    private void inicializarColaborador(){
        colaborador = FactoryInstanciasParaTests.instanciarColaboradorHumano();
    }

    // <---------------------- Testeos ----------------------> //

    @Nested
    @DisplayName("Mensajes creados ante un evento relacionado a viandas")
    public class TestsEventosDeViandas{

        @Test
        @DisplayName("Se creo el mensaje ante el evento: quedan n viandas")
        public void testQuedanNViandas(){
            String mensaje = CreadorDeMensajes.crearMensaje("Quedan 5 viandas", heladera);
            assertEquals("Quedan 5 viandas en la heladera: Heladera Medrano UTN (Medrano 981).", mensaje);
        }
    }

    @Nested
    @DisplayName("Mensajes creados ante un evento relacionado a fallas en heladeras")
    public class TestsEventosDeFallasDeHeladera {
        @Disabled
        @Test
        @DisplayName("Se creo el mensaje ante el evento: se produjo una falla, pero no sugirio heladeras ya que no habian")
        public void testQuedanNViandas(){
            String mensaje = CreadorDeMensajes.crearMensaje("Se produjo una falla\n", heladera);
            assertEquals("Se produjo una falla en la heladera: Heladera Medrano UTN (Medrano 981).\n" +
                    "No hay heladeras disponibles para redistribucion.", mensaje);
        }
    }

    //TODO: Un test relacionado a devolver heladeras sugeridas
}
