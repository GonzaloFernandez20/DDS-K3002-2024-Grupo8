package TestUnitarios.PaqueteIncidentes;

import Modelo.Dominio.incidentes.*;
import Modelo.Dominio.tecnico.Tecnico;
import org.junit.jupiter.api.*;


import static Modelo.Dominio.incidentes.EstadoDelIncidente.PENDIENTE;
import static Modelo.Dominio.incidentes.EstadoDelIncidente.SOLUCIONADO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Pruebas relacionadas con los incidentes:")
public class TestDeIncidentes {
    static FallaTecnica fallaTecnica;
    static Alerta alerta;
    private VisitaTecnica visitaTecnica;

    // Aca seteo lo necesario para los tests
    @BeforeAll
    public static void instanciasDeIncidentes(){
         fallaTecnica = new FallaTecnica(null, "Falla en la visagra", null, null);
         alerta = new Alerta(TipoAlerta.TEMPERATURA, null);
    }

    @Nested
    @DisplayName("Ocurre un incidente en una heladera")
    class TestSobreCreacionDeIncidentes{

        @Test
        @DisplayName("La informacion obtenida acerca de una Falla Tecnica es correcta")
        public void testDeInformacionDeFalla(){
            String infoDeLaFalla = fallaTecnica.obtenerInformacion();
            String infoEsperadaDeLaFalla = "una Falla Tecnica: \n" + "Descripcion: " + "Falla en la visagra" + "\n" + "Link Foto: " + null;
            assertEquals(infoDeLaFalla, infoEsperadaDeLaFalla, "Se obtuvo info de la falla");
        }

        @Test
        @DisplayName("La informacion obtenida acerca de una Alerta es correcta")
        public void testDeInformacionDeAlerta() {
            String infoDeLaAlerta = alerta.obtenerInformacion();
            String infoEsperadaDeAlerta = "una Alerta de tipo: " + "TEMPERATURA";
            assertEquals(infoDeLaAlerta, infoEsperadaDeAlerta, "Se obtuvo info de la alerta");
        }
        @Test
        @DisplayName("Al reportarse un incidente, su estado es \"PENDIENTE\" hasta que acuda un tecnico")
        public void testDeCreacionDeIncidentes(){

            GestorDeIncidentes.reportar(fallaTecnica);
            GestorDeIncidentes.reportar(alerta);
            assertAll(
                    () -> assertEquals(PENDIENTE, fallaTecnica.getEstado()),
                    () -> assertEquals(PENDIENTE, alerta.getEstado()) );
        }
    }

    @Nested
    @DisplayName("Un Tecnico acude a una heladera ante una Falla Tecnica o Alerta: ")
    class TestSobreVisitaTecnica{

        @Test
        @DisplayName("El tecnico pudo solucionar el incidente, quedo en estado: \"SOLUCIONADO\"")
        public void testDeEstadoLuegoDeVisitaTecnicaExitosa(){
            // Testeo del estado luego de que un tecnico acuda a la heladera
            assertAll("Verificacion de estado de los incidentes",
                    () ->assertEquals(SOLUCIONADO, fallaTecnica.getEstado()),
                    () ->assertEquals(SOLUCIONADO, alerta.getEstado()) );

        }

        @Test
        @DisplayName("El tecnico no pudo solucionar el incidente, quedo en estado: \"NO_SOLUCIONADO\"")
        public void testDeEstadoLuegoDeVisitaTecnicaFallida(){
            // Testeo del estado luego de que un tecnico acuda a la heladera
        }

        @Test
        @DisplayName("El tecnico pudo solucionar el incidente luego de 3 visitas a la heladera")
        public void testDeIteracionDeVisitas(){
            // Testeo del estado luego de que un tecnico acuda a la heladera
        }
    }

    @Nested
    @DisplayName("Testeos sobre funcionalidad de alertas")
    class TestSobreAlerta{

    }
}
/*
@DisplayName("Clase de Pruebas para la Calculadora")
class CalculadoraTest {

    Calculadora calculadora = new Calculadora();

    @Test
    @DisplayName("Prueba de suma: 2 + 3 debe dar 5")
    void sumar_debeRetornarElResultadoCorrecto() {
        assertEquals(5, calculadora.sumar(2, 3));
    }

    @Nested
    @DisplayName("Pruebas para la funcionalidad de división")
    class PruebasDeDivision {

        @Test
        @DisplayName("División: 6 / 3 debe dar 2")
        void dividir_debeRetornarElResultadoCorrecto() {
            assertEquals(2, calculadora.dividir(6, 3));
        }

        @Test
        @DisplayName("División por cero debe lanzar una ArithmeticException")
        void dividir_debeLanzarExceptionCuandoSeDividePorCero() {
            assertThrows(ArithmeticException.class, () -> calculadora.dividir(6, 0));
        }
    }
}
*
*
* */
