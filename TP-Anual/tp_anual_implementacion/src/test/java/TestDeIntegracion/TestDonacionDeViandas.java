package TestDeIntegracion;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeVianda;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDonacionDeViandas {
    DonacionDeVianda donacionDeVianda;
    Heladera heladeraDestino;
    Colaborador colaborador;
    List<Vianda> viandasDeLaDonacion = new ArrayList<>();
    Vianda viandaEnsalada = FactoryInstanciasParaTests.instanciarVianda("Ensalada", LocalDate.of(2025,12,2));

    @BeforeEach
    void setUp(){
        Vianda viandaTortilla = FactoryInstanciasParaTests.instanciarVianda("Tortilla", LocalDate.of(2025,12,2));
        Vianda viandaPancho = FactoryInstanciasParaTests.instanciarVianda("Pancho", LocalDate.of(2025,12,2));
        Vianda viandaSopa = FactoryInstanciasParaTests.instanciarVianda("Sopa", LocalDate.of(2025,12,2));
        List<Vianda> viandasDonadas = new ArrayList<>();
        viandasDonadas.add(viandaSopa);
        viandasDonadas.add(viandaTortilla);
        viandasDonadas.add(viandaPancho);
        viandasDeLaDonacion.addAll(viandasDonadas);

        donacionDeVianda = new DonacionDeVianda(colaborador= FactoryInstanciasParaTests.instanciarColaboradorHumano(),
                                                heladeraDestino = FactoryInstanciasParaTests.instanciarUnaHeladera(),
                                                viandasDonadas,
                                                LocalDate.now());
    }

        @Test
        @DisplayName("Una donacion de 3 viandas son 4,5 puntos")
        void puntaje(){
            assertEquals(4.5,donacionDeVianda.puntosQueSumaColaborador());
        }

        @Nested
        @DisplayName("Caso de prueba: todas las viandas de la donacion pudieron ser ingresadas en la heladera")
        class viandasIngresadas{

            @Test
            @DisplayName("La heladera destino contiene todas las viandas")
            void heladeraConViandas() {
                donacionDeVianda.procesarLaContribucion();
                assertTrue(heladeraDestino.getViandasEnStock().containsAll(viandasDeLaDonacion));
            }

            @Test
            @DisplayName("La donacion queda registrada con todas las viandas donadas")
            void todasLasViandas() {
                donacionDeVianda.procesarLaContribucion();
                assertEquals(viandasDeLaDonacion, donacionDeVianda.getViandas());
            }

            @Test
            @DisplayName("El estado de las viandas donadas es ENTREGADO")
            void viandasEntregadas() {
                donacionDeVianda.procesarLaContribucion();
                assertTrue(viandasDeLaDonacion.stream().allMatch(vianda -> vianda.getEstado().equals(EstadoVianda.ENTREGADA)));
            }

            @Test
            @DisplayName("El colaborador tiene registrada la contribucion de donacion de viandas")
            void regsitroContribucion() {
                donacionDeVianda.procesarLaContribucion();
                assertTrue(colaborador.getHistorialDeContribuciones().contains(donacionDeVianda));
            }
        }

        @Nested
        @DisplayName("Caso de prueba: en la heladera destino aumento el stock y no entran todas las viandas")
        class algunasViandasIngresadas{
            @Test
            @DisplayName("La heladera destino contiene solo las viandas que entraron")
            void heladeraConViandas() {
                heladeraDestino.recibirVianda(viandaEnsalada);
                donacionDeVianda.procesarLaContribucion();
                assertFalse(heladeraDestino.getViandasEnStock().containsAll(viandasDeLaDonacion));
                assertTrue(heladeraDestino.getViandasEnStock().containsAll(donacionDeVianda.getViandas()));
            }

            @Test
            @DisplayName("La donacion queda registrada solo con las viandas que pudo ingresar el colaborador")
            void todasLasViandas() {
                heladeraDestino.recibirVianda(viandaEnsalada);
                donacionDeVianda.procesarLaContribucion();
                assertNotEquals(viandasDeLaDonacion, donacionDeVianda.getViandas());
            }

            @Test
            @DisplayName("Las viandas que no ingresaron en la heladera permanecen en estado NO_ENTREGADA")
            void viandasEntregadas() {
                heladeraDestino.recibirVianda(viandaEnsalada);
                donacionDeVianda.procesarLaContribucion();
                assertTrue(viandasDeLaDonacion.stream().anyMatch(vianda -> vianda.getEstado().equals(EstadoVianda.NO_ENTREGADA)));
            }

            @Test
            @DisplayName("El colaborador tiene registrada la contribucion de donacion de viandas")
            void regsitroContribucion() {
                heladeraDestino.recibirVianda(viandaEnsalada);
                donacionDeVianda.procesarLaContribucion();
                assertTrue(colaborador.getHistorialDeContribuciones().contains(donacionDeVianda));
            }

            @Test
            @DisplayName("El colaborador suma puntos solo por las viandas que pudo ingresar")
            void puntosColaborador() {
                heladeraDestino.recibirVianda(viandaEnsalada);
                donacionDeVianda.procesarLaContribucion();
                assertEquals(3, colaborador.getPuntosAcumulados());
            }
        }
}


