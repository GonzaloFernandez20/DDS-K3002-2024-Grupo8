package TestUnitarios.PaqueteHeladera;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Excepciones.ExcepecionViandasRechazadas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeladera {
    Heladera heladera;
    Vianda viandaArroz = new Vianda("Arroz", null,null,null,null,null);
    Vianda viandaMilanesa = new Vianda("Milanesa", null,null,null,null,null);
    Vianda viandaPollo = new Vianda("Pollo", null,null,null,null,null);
    Vianda viandaEnsalada = new Vianda("Ensalada", null,null,null,null,null);
    Vianda viandaTortilla = new Vianda("Tortilla", null,null,null,null,null);
    List<Vianda> ingresoDeViandas1 = Arrays.asList(viandaEnsalada, viandaArroz, viandaMilanesa);
    List<Vianda> ingresoDeViandas2 = Arrays.asList(viandaPollo, viandaTortilla);
    List<Vianda> ingresoDeViandas3 = Arrays.asList(viandaEnsalada, viandaArroz, viandaMilanesa,viandaPollo, viandaTortilla,viandaEnsalada);

    @BeforeEach
    void setUpHeladeraNueva(){
        heladera = new Heladera(null,
                                     new Ubicacion( new Direccion("Medrano", "981",null),"CABA", "Heladera Medrano UTN"),
                                     5,
                                     null,
                                     null);
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);
    }

    @Nested
    class PruebasEstadoDeLaHeladera {
        @Test
        @DisplayName("La heladera cuando se da de alta esta activa")
        void heladeraActiva(){
            assertEquals(EstadoHeladera.ACTIVA, heladera.getEstado());
        }
        @Test
        @DisplayName("Al ocurrile un incidente la heladera queda inactiva")
        void heladeraInactiva(){
            heladera.huboIncidente();
            assertEquals(EstadoHeladera.INACTIVA, heladera.getEstado());
        }
    }

    @Nested
    class PruebasDeIngresoDeViandas{
        @Test
        @DisplayName("Todas las viandas del stock corresponden al ingreso efectuado")
        void ingresarViandasEnHeladeraVacia(){
            heladera.recibirViandas(ingresoDeViandas1);
            assertAll(
                    () -> assertEquals(ingresoDeViandas1, heladera.getViandasEnStock()),
                    () -> assertTrue(ingresoDeViandas1.size() == heladera.getViandasEnStock().size())
            );
        }
        @Test
        @DisplayName("Las viandas ingresadas se suman a las viandas actuales del stock")
        void ingresarViandasEnHeladeraConViandas(){
            heladera.recibirViandas(ingresoDeViandas1);
            heladera.recibirViandas(ingresoDeViandas2);
            assertAll(
                    () -> assertNotEquals(ingresoDeViandas2, heladera.getViandasEnStock()),
                    () -> assertTrue(heladera.getViandasEnStock().containsAll(ingresoDeViandas2)),
                    () -> assertTrue(ingresoDeViandas1.size() < heladera.getViandasEnStock().size())
            );
        }
        @Test
        @DisplayName("La cantidad de viandas a ingresar supera el espacio disponible o la heladera esta llena")
        void lasViandasAIngresarNoEntranEnLaHeladera(){
            ExcepecionViandasRechazadas exception = assertThrows(ExcepecionViandasRechazadas.class,
                    () -> {heladera.recibirViandas(ingresoDeViandas3);});
        }
    }

    @Nested
    class PruebasDeRetiroDeViandas {
        @Test
        @DisplayName("La cantidad de viandas a retirar es menor a la cantidad de viandas en el stock")
        void stockSuficiente(){

        }
        @Test
        @DisplayName("La cantidad de viandas a retirar es exáctamente la cantidad de viandas del stock")
        void stockQuedaVacio(){

        }
        @Test
        @DisplayName("La cantidad de viandas a retirar es mayor a la cantidad de viandas en el stock")
        void stockInsuficiente(){

        }
        @Test
        @DisplayName("La heladera no tiene viandas a retirar")
        void stockEstaVacio(){

        }

    }

    @Nested
    class PruebasCantidadDeViandas {
        @Test
        @DisplayName("No hay viandas en stock")
        void heladeraVacia(){
            assertTrue(heladera.getViandasEnStock().isEmpty());
        }
        @Test
        @DisplayName("HayViandasEnStock")
        void heladeraTieneViandas(){

        }
    }

    @Nested
    class PruebasEspacioDisponible {
        @Test
        @DisplayName("La cantidad de viandas en stock corresponde a la capacidad máxima de la heladera")
        void noHayEspacioDisponible(){

        }
        @Test
        @DisplayName("El stock no ocupa la capacidad máxima de la heladera")
        void quedaEspacioDisponible(){
            assertTrue(heladera.espacioDisponible()>0);
            //una heladaer con viandas
        }
    }

}
