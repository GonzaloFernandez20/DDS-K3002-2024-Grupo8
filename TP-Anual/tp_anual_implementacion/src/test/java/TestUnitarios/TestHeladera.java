package TestUnitarios;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static Modelo.Dominio.heladera.EstadoHeladera.ACTIVA;
import static Modelo.Dominio.heladera.EstadoHeladera.INACTIVA;
import static org.junit.jupiter.api.Assertions.*;

public class TestHeladera {
    Heladera heladera;
    Vianda viandaArroz = FactoryInstanciasParaTests.instanciarVianda("Arroz", LocalDate.of(2025,12,2));
    Vianda viandaMilanesa = FactoryInstanciasParaTests.instanciarVianda("Milanesa", LocalDate.of(2025,12,2));
    Vianda viandaPollo = FactoryInstanciasParaTests.instanciarVianda("Pollo", LocalDate.of(2025,12,2));
    Vianda viandaEnsalada = FactoryInstanciasParaTests.instanciarVianda("Ensalada", LocalDate.of(2025,12,2));
    List<Vianda> ingresoDeViandas = Arrays.asList(viandaPollo, viandaArroz, viandaMilanesa);

    // <---------------------- Configuraciones ----------------------> //
    void setUpHeladeraVacia() {
        heladera = FactoryInstanciasParaTests.instanciarUnaHeladera();
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);
    }

    void setUpHeladeraLlena() {
        setUpHeladeraVacia();
        heladera.getViandasEnStock().addAll(ingresoDeViandas);
    }

    void setUpHeladeraConVianda() {
        setUpHeladeraVacia();
        heladera.getViandasEnStock().add(viandaArroz);
    }

    // <---------------------- Testeos ----------------------> //
    @Nested
    class PruebasEstadoDeLaHeladera {
        @Test
        @DisplayName("La heladera cuando se da de alta esta activa")
        void heladeraActiva() {
            setUpHeladeraVacia();
            assertEquals(ACTIVA, heladera.getEstado());
        }

        @Test
        @DisplayName("Al ocurrirle un incidente la heladera queda inactiva")
        void heladeraInactiva() {
            setUpHeladeraVacia();
            heladera.huboIncidente();
            assertEquals(INACTIVA, heladera.getEstado());
        }
    }

    @Nested
    class PruebasDeIngresoDeVianda {
        @Test
        @DisplayName("Se puede ingresar una vianda cuando queda espacio en la heladera")
        void laHeladeraTieneEspacioDisponible() {
            setUpHeladeraVacia();
            heladera.recibirVianda(viandaMilanesa);
            assertTrue(heladera.getViandasEnStock().contains(viandaMilanesa));
        }

        @Test
        @DisplayName("No se puede ingresar una vianda cuando la heladera esta llena")
        void laHeladeraEstaLlena() {
            setUpHeladeraLlena();
            ExcepcionHeladeraLlena exception = assertThrows(ExcepcionHeladeraLlena.class,
                    () -> heladera.recibirVianda(viandaEnsalada));
            assertEquals("La heladera esta llena, no entran más viandas",exception.getMessage());
            assertFalse(heladera.getViandasEnStock().contains(viandaEnsalada));
        }
    }

    @Nested
    class PruebasDeRetiroDeViandas {

        @Nested
        @DisplayName("Caso de prueba: La cantidad de viandas a retirar es menor a la cantidad de viandas en el stock")
        class stockSuficiente {
            int cantidadARetirar = 2;
            List<Vianda> viandasRetiradas;

            void setUp() {
                setUpHeladeraLlena();
                viandasRetiradas = heladera.retirarViandas(cantidadARetirar);
            }

            @Test
            @DisplayName("Las viandas retiradas ya no estan en el stock de la heladera")
            void fueronRetiradas() {
                setUp();
                assertFalse(heladera.getViandasEnStock().containsAll(viandasRetiradas));
            }

            @Test
            @DisplayName("Se retiro la cantidad de viandas solicitada")
            void seRetiroLaCantidadDeseada() {
                setUp();
                assertEquals(cantidadARetirar, viandasRetiradas.size());
            }
        }

        @Nested
        @DisplayName("Caso de prueba: La cantidad de viandas a retirar es exáctamente la cantidad de viandas del stock")
        class stockJusto {
            @Test
            @DisplayName("El stock queda vacio al retirar todas las viandas")
            void stockQuedaVacio() {
                setUpHeladeraLlena();
                heladera.retirarViandas(heladera.getViandasEnStock().size());
                assertEquals(0, heladera.cantViandasEnStock());
            }

            @Test
            @DisplayName("Las viandas restiradas corresponden al stock completo de la heladera previo a vaciarla")
            void elRetiroEsTodoElStock() {
                setUpHeladeraLlena();
                List<Vianda> stockPrevioARetirar = new ArrayList<>(heladera.getViandasEnStock());
                List<Vianda> viandasretiradas = heladera.retirarViandas(stockPrevioARetirar.size());
                assertEquals(stockPrevioARetirar, viandasretiradas);
            }

        }

        @Nested
        @DisplayName("Caso de prueba: La cantidad de viandas a retirar es mayor a la cantidad de viandas en el stock")
        class stockInsuficiente {
            @Test
            @DisplayName("Solo se retiran las viandas que quedan en la heladera")
            void seRetiraLoQueQueda(){
                setUpHeladeraConVianda();
                int cantidadARetirar = heladera.cantViandasEnStock()+2;
                List<Vianda> viandasQueQuedan = new ArrayList<>(heladera.getViandasEnStock());
                List<Vianda> viandasretiradas = heladera.retirarViandas(cantidadARetirar);
                assertAll(
                        ()->assertEquals(viandasQueQuedan, viandasretiradas),
                        ()->assertTrue(cantidadARetirar > viandasretiradas.size())
                );
            }

            @Test
            @DisplayName("El stock queda vacio al retirar las viandas que quedan")
            void stockQuedaVacio() {
                setUpHeladeraConVianda();
                heladera.retirarViandas(5);
                assertEquals(0, heladera.cantViandasEnStock());
            }
        }

        @Test
        @DisplayName("Caso prueba: la heladera esta vacia por lo que no se retiran viandas")
        void stockEstaVacio() {
            setUpHeladeraVacia();
            assertEquals(Collections.emptyList(), heladera.retirarViandas(3));
        }
    }

    @Nested
    class PruebasCantidadDeViandas {
        @Test
        @DisplayName("La cantidad de viandas en la heladera es 0 cuando esta vacia")
        void heladeraVacia() {
            setUpHeladeraVacia();
            assertEquals(0, heladera.cantViandasEnStock());
        }

        @Test
        @DisplayName("La cantidad de viadas es mayor que cero cuando hay viandas en stock")
        void heladeraTieneViandas() {
            setUpHeladeraLlena();
            assertTrue(heladera.cantViandasEnStock() > 0);
        }
    }
}

