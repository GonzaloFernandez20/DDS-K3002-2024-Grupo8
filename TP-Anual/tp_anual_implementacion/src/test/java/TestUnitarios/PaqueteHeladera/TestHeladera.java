package TestUnitarios.PaqueteHeladera;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Excepciones.ExcepcionHeladeraLlena;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static Modelo.Dominio.heladera.EstadoHeladera.ACTIVA;
import static Modelo.Dominio.heladera.EstadoHeladera.INACTIVA;
import static org.junit.jupiter.api.Assertions.*;

public class TestHeladera {
    Heladera heladera;
    Vianda viandaArroz = new Vianda("Arroz", null, null, null, null, null);
    Vianda viandaMilanesa = new Vianda("Milanesa", null, null, null, null, null);
    Vianda viandaPollo = new Vianda("Pollo", null, null, null, null, null);
    Vianda viandaEnsalada = new Vianda("Ensalada", null, null, null, null, null);
    List<Vianda> ingresoDeViandas = Arrays.asList(viandaPollo, viandaArroz, viandaMilanesa);


    void setUpHeladeraVacia() {
        heladera = new Heladera(null,
                new Ubicacion(new Direccion("Medrano", "981", null), "CABA", "Heladera Medrano UTN"),
                3,
                null,
                null);
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
                    () -> {
                        heladera.recibirVianda(viandaEnsalada);
                    });
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
                assertTrue(heladera.cantViandasEnStock() == 0);
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
                assertTrue(heladera.cantViandasEnStock() == 0);
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
            assertTrue(heladera.cantViandasEnStock() == 0);
        }

        @Test
        @DisplayName("La cantidad de viadas es mayor que cero cuando hay viandas en stock")
        void heladeraTieneViandas() {
            setUpHeladeraLlena();
            assertTrue(heladera.cantViandasEnStock() > 0);
        }
    }
}

