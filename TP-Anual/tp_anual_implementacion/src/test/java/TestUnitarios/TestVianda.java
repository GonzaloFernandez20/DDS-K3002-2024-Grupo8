package TestUnitarios;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static Modelo.Dominio.contribucion.EstadoVianda.EN_TRASLADO;
import static Modelo.Dominio.heladera.EstadoHeladera.ACTIVA;
import static Modelo.Dominio.heladera.EstadoHeladera.INACTIVA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestVianda {
    Vianda vianda;
    Heladera heladerActual = FactoryInstanciasParaTests.instanciarUnaHeladera();
    Heladera heladeraNueva = FactoryInstanciasParaTests.instanciarOtraHeladera();

    void setUp(){
        vianda = FactoryInstanciasParaTests.instanciarVianda("Pastel de Papa", LocalDate.of(2025,12,2));
    }
    @Nested
    class PruebasTrasladoDeVianda {
        @Test
        @DisplayName("Al trasladar una vianda se actualiza la heladera en la que se encuentra")
        void cambioDeHeladera() {
            setUp();
            vianda.trasladar(heladeraNueva);
            assertEquals(heladeraNueva,vianda.getHeladera());
        }

        @Test
        @DisplayName("Al trasladar una vianda cambia su estado a EN_TRASLADO")
        void cambioDeEstadoVianda() {
            setUp();
            vianda.trasladar(heladeraNueva);
            assertEquals(EN_TRASLADO,vianda.getEstado());
        }

        @Test
        @DisplayName("Trasladar una vianda a la misma heladera no cambia nada")
        void trasladarALaMismaHeladeraNoCambiaNada() {
            setUp();
            vianda.trasladar(heladerActual);
            assertEquals(heladerActual,vianda.getHeladera());
            assertNotEquals(EN_TRASLADO,vianda.getEstado());

        }
    }
}
