package TestDeIntegracion;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.sistema.RegistroDeRecaudacion;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static Modelo.Dominio.contribucion.Frecuencia.MENSUALMENTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DisplayName("Prueba integral: Donacion de Dinero")
public class TestDonacionDeDinero {
    Colaborador colaborador;
    DonacionDeDinero donacionDeDinero;

    // <---------------------- Configuraciones ----------------------> //

    @BeforeEach
    public void configuracionInicial(){
        colaborador = FactoryInstanciasParaTests.instanciarColaboradorHumano();
        donacionDeDinero = new DonacionDeDinero(colaborador, 45068, MENSUALMENTE, LocalDate.now());
        RegistroDeRecaudacion.getInstancia().setFondoRecaudado(0);
        donacionDeDinero.procesarLaContribucion();
    }

    // <---------------------- Testeos ----------------------> //
    @Nested
    @DisplayName("Se produce una Donacion de Dinero, el impacto se ve reflejado en el sistema")
    class TestDeChequeoDeTestLaDonacion{
        @Test
        @DisplayName("El monto se ve impactado en la recaudacion del sistema")
        public void atestChequeoDeFondoDeRecaudacion(){
            assertEquals(45068, RegistroDeRecaudacion.getInstancia().getFondoRecaudado());
        }

        @Test
        @DisplayName("La donacion queda registrada en el historial del colaborador")
        public void btestChequeoDeContribucionesDelColab(){
            assertTrue(colaborador.getHistorialDeContribuciones().contains(donacionDeDinero));
        }

        @Test
        @DisplayName("El colaborador obtiene puntos por su colaboracion")
        public void ctestColaboradorSumaLosPuntos(){
            assertEquals(45068 * 0.5, colaborador.getPuntosAcumulados());
        }
    }

    @Nested
    @DisplayName("Se producen varias Donaciones de Dinero")
    class TestVariasContribuciones {
        float fondoAcumulado = 45068;
        @Test
        @DisplayName("Al producirse varias donaciones, todas quedas acumuladas")
        public void dtestVariasDonacionesImpactando(){
            DonacionDeDinero donacionDeDinero2 = new DonacionDeDinero(colaborador, 12000, MENSUALMENTE, LocalDate.now());
            donacionDeDinero2.procesarLaContribucion();
            fondoAcumulado += 12000;
            assertEquals(fondoAcumulado, RegistroDeRecaudacion.getInstancia().getFondoRecaudado());

            DonacionDeDinero donacionDeDinero3 = new DonacionDeDinero(colaborador, 154890, MENSUALMENTE, LocalDate.now());
            donacionDeDinero3.procesarLaContribucion();
            fondoAcumulado += 154890;
            assertEquals(fondoAcumulado, RegistroDeRecaudacion.getInstancia().getFondoRecaudado());
        }
    }
}
