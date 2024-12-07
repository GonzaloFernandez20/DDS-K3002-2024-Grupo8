package TestDeIntegracion;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.sistema.RegistroDeRecaudacion;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static Modelo.Dominio.contribucion.Frecuencia.MENSUALMENTE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Prueba integral: Donacion de Dinero")
public class TestDonacionDeDinero {
    Colaborador colaborador;
    DonacionDeDinero donacionDeDinero;

    // <---------------------- Configuraciones ----------------------> //

    @BeforeEach
    public void configuracionInicial(){
        colaborador = FactoryInstanciasParaTests.instanciarColaboradorHumano();
        donacionDeDinero = new DonacionDeDinero(colaborador, 45068, MENSUALMENTE, LocalDate.now());
        //RegistroDeRecaudacion.getInstancia().setFondoRecaudado(0);
        donacionDeDinero.procesarLaContribucion();
    }

    @AfterEach
    public void resetearFondoDeRecaudacion() throws Exception {
        // Usamos Reflection (Solo en el contexto de este test) para evitar que se pueda setear manualmente el valor del fondo
        Field fondoRecaudado = RegistroDeRecaudacion.class.getDeclaredField("fondoRecaudado");
        fondoRecaudado.setAccessible(true);
        fondoRecaudado.set(RegistroDeRecaudacion.getInstancia(), 0);
    }

    // <---------------------- Testeos ----------------------> //
    @Nested
    @DisplayName("Se produce una Donacion de Dinero, el impacto se ve reflejado en el sistema")
    class TestDeUnaDonacionDeDinero{

        @Test
        @DisplayName("El monto se ve impactado en la recaudacion del sistema")
        public void testChequeoDeFondoDeRecaudacion(){
            assertEquals(45068, RegistroDeRecaudacion.getInstancia().getFondoRecaudado());
        }

        @Test
        @DisplayName("La donacion queda registrada en el historial del colaborador")
        public void testChequeoDeContribucionesDelColab(){
            assertTrue(colaborador.getHistorialDeContribuciones().contains(donacionDeDinero));
        }

        @Test
        @DisplayName("El colaborador obtiene puntos por su colaboracion")
        public void testColaboradorSumaLosPuntos(){
            assertEquals(45068 * 0.5, colaborador.getPuntosAcumulados());
        }
    }

    @Disabled("Debemos cachear excepciones en estos casos")
    @Nested
    @DisplayName("Ante montos invalidos en una donacion, se rechaza la misma")
    class TestDonacionesInvalidas{
        @Test
        @DisplayName("No se permite una donación de monto cero")
        public void noPermitirDonacionConMontoCero() {
            DonacionDeDinero donacionCero = new DonacionDeDinero(colaborador, 0, MENSUALMENTE, LocalDate.now());
            assertThrows(IllegalArgumentException.class, donacionCero::procesarLaContribucion);
        }

        @Test
        @DisplayName("No se permite una donación de monto negativo")
        public void noPermitirDonacionConMontoNegativo() {
            DonacionDeDinero donacionNegativa = new DonacionDeDinero(colaborador, -1000, MENSUALMENTE, LocalDate.now());
            assertThrows(IllegalArgumentException.class, donacionNegativa::procesarLaContribucion);
        }

    }

    @Nested
    @DisplayName("Se producen varias Donaciones de Dinero")
    class TestVariasContribuciones {
        float fondoAcumulado = 45068;
        @Test
        @DisplayName("Al producirse varias donaciones, todas quedas acumuladas")
        public void testVariasDonacionesImpactando(){
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
