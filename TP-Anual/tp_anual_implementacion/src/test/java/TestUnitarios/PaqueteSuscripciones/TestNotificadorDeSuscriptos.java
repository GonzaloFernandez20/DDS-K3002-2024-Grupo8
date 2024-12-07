package TestUnitarios.PaqueteSuscripciones;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testeo de notificaciones a suscriptos ante un evento")
public class TestNotificadorDeSuscriptos {
    Heladera heladera;
    Colaborador colaborador;
    NotificadorDeSuscriptos notificadorDeSuscriptos;

    // <---------------------- Configuraciones ----------------------> //
    @BeforeEach
    public void configuracionInicial(){
        inicializarHeladera();
        inicializarColaborador();
        notificadorDeSuscriptos = new NotificadorDeSuscriptos(heladera);
    }

    private void inicializarHeladera(){
        heladera = FactoryInstanciasParaTests.instanciarOtraHeladera();
    }

    private void inicializarColaborador(){
        colaborador = FactoryInstanciasParaTests.instanciarColaboradorHumano();
    }
    // <---------------------- Testeos ----------------------> //

    @Nested
    @DisplayName("Comportamiento de claves dentro del Hash")
    class TestDeEventosDeSuscripciones {

        @Test
        @DisplayName("Se agrego un evento que no existia previamente")
        public void seAgregoUnEvento(){
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            assertTrue(notificadorDeSuscriptos.getSuscriptos().containsKey("se produjo una falla"));
        }

        @Test
        @DisplayName("Cuando no quedan colaboradores asociados a un evento, este se elimina de la lista de suscripciones")
        public void seRemovioUnaClave(){
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.desuscribir("se produjo una falla", colaborador);
            assertFalse(notificadorDeSuscriptos.getSuscriptos().containsKey("se produjo una falla"));
        }

        @Test
        @DisplayName("Intentar desuscribirse de un evento no existente")
        public void desuscribirseEventoNoExistente() {
            assertThrows(NullPointerException.class, () -> notificadorDeSuscriptos.desuscribir("evento inexistente", colaborador));
        } // TODO: Manejo del error?
    }

    @Nested
    @DisplayName("Un colaborador se suscribe a un evento ya existente")
    class TestSuscripcionDeUnColaborador{

        @Test
        @DisplayName("El colaborador quedo suscripto a un evento")
        public void colaboradorSeSuscribeAEvento(){
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            assertTrue(notificadorDeSuscriptos.getSuscriptos().get("se produjo una falla").contains(colaborador));
        }

        @Test
        @DisplayName("Varios colaboradores pueden desuscribirse de un evento")
        public void colaboradoresSeDesuscribenAEvento(){
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);

            notificadorDeSuscriptos.desuscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.desuscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.desuscribir("se produjo una falla", colaborador);
            assertEquals(2, notificadorDeSuscriptos.getSuscriptos().get("se produjo una falla").size());
        }

        @Test
        @DisplayName("Varios colaboradores pueden suscribirse a varios eventos")
        public void colaboradorSuscriptoAVariosEventos(){
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("se produjo una falla", colaborador);
            notificadorDeSuscriptos.suscribir("quedan 5 viandas", colaborador);
            notificadorDeSuscriptos.suscribir("quedan 15 viandas", colaborador);
            notificadorDeSuscriptos.suscribir("quedan 15 viandas", colaborador);

            assertAll( () -> assertEquals(3, notificadorDeSuscriptos.getSuscriptos().get("se produjo una falla").size()),
                       () -> assertEquals(1, notificadorDeSuscriptos.getSuscriptos().get("quedan 5 viandas").size()),
                       () -> assertEquals(2, notificadorDeSuscriptos.getSuscriptos().get("quedan 15 viandas").size()));
        }
    }

    @Nested
    @DisplayName("Un evento recibe suscripciones de manera masiva")
    class TestDeSuscripcionMasiva{

        @Test
        @DisplayName("Un gran numero de colabores se suscriben a un evento")
        public void agregarNumeroDeColaboradores() {
            for (int i = 0; i < 1000; i++) {
                Colaborador nuevoColaborador = FactoryInstanciasParaTests.instanciarColaboradorHumano();
                notificadorDeSuscriptos.suscribir("se produjo una falla", nuevoColaborador);
            }
            assertEquals(1000, notificadorDeSuscriptos.getSuscriptos().get("se produjo una falla").size());
        }
    }
}
