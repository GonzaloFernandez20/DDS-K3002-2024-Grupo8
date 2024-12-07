package TestUnitarios;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static Modelo.Dominio.contribucion.EstadoVianda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestVianda {
    Vianda vianda;
    Heladera heladeraNueva = FactoryInstanciasParaTests.instanciarOtraHeladera();

    // <---------------------- Configuraciones ----------------------> //
    void setUp(){
        vianda = FactoryInstanciasParaTests.instanciarVianda("Pastel de Papa", LocalDate.of(2025,12,2));
    }
    void setUpViandaVencida(){
        vianda = FactoryInstanciasParaTests.instanciarVianda("Pescado", LocalDate.of(2024,9,2));
    }

    // <---------------------- Testeos ----------------------> //
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
        @DisplayName("Trasladar una vianda a la misma heladera no cambia su estado")
        void trasladarALaMismaHeladeraNoCambiaNada() {
            setUp();
            vianda.trasladar(vianda.getHeladera());
            assertNotEquals(EN_TRASLADO,vianda.getEstado());
        }
    }
    @Nested
    class PruebasEstadoDeVianda {
        @Test
        @DisplayName("Al crearse una vianda en condiciones su estado es NO_ENTREGADA")
        void viandaNoEntregada() {
            setUp();
            assertEquals(NO_ENTREGADA,vianda.getEstado());
        }

        @Test
        @DisplayName("Al crearse una vianda cuya fecha de caducidad ya pasó su estado es VENCIDA")
        void viandaVencida() {
            setUpViandaVencida();
            assertEquals(VENCIDA,vianda.getEstado());
        }
    }
}
