package TestDeIntegracion;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.Accesos_a_heladeras.GestorDeAccesosAHeladeras;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


@DisplayName("Prueba integral: Retiro de una vianda por parte de una PeSV")
public class TestRetiroDeViandas {
    PersonaSituacionVulnerable personaSituacionVulnerable;
    GestorDeAccesosAHeladeras gestorDeAccesosAHeladeras;
    Heladera heladeraDeRetiro;

    // <---------------------- Configuraciones ----------------------> //

    @BeforeEach
    public void configuracionInicial(){
        personaSituacionVulnerable = FactoryInstanciasParaTests.instanciarPersonaEnSV();
        gestorDeAccesosAHeladeras = GestorDeAccesosAHeladeras.getInstancia();
        heladeraDeRetiro = FactoryInstanciasParaTests.instanciarOtraHeladera();
        for (int i = 0; i < 10; i++){
            Vianda vianda = FactoryInstanciasParaTests.instanciarVianda("Milanga", LocalDate.of(2024, 10, 14));
            heladeraDeRetiro.recibirVianda(vianda);
        }
    }

    @AfterEach
    public void limpiezaGeneral(){
        heladeraDeRetiro.retirarViandas(heladeraDeRetiro.getViandasEnStock().size()); // -> Vaciamos la heladera
        personaSituacionVulnerable = null;
    }

    // <---------------------- Testeos ----------------------> //


    @Nested
    @DisplayName("El lector identifica la tarjeta y autoriza o no la apertura")
    class TestDeAutorizacionDeAperturas{

        @Test
        @DisplayName("Una tarjeta valida con usos restantes recibe su autorizacion")
        public void testAutorizacionDeApertura1(){
            assertTrue(gestorDeAccesosAHeladeras.autorizarApertura("AG780EX", heladeraDeRetiro));
        }

        @Test
        @DisplayName("Una tarjeta valida sin usos restantes no recibe su autorizacion")
        public void testAutorizacionDeApertura2(){
            for (int i = 0; i < 8; i++) {
                gestorDeAccesosAHeladeras.autorizarApertura("AG780EX", heladeraDeRetiro);
            } // -> Consumio todos los usos permitidos en un dia
            assertFalse(gestorDeAccesosAHeladeras.autorizarApertura("AG780EX", heladeraDeRetiro));
        }

        @Test
        @DisplayName("Una tarjeta invalida no recibe su autorizacion")
        public void testAutorizacionDeApertura3(){
            assertFalse(gestorDeAccesosAHeladeras.autorizarApertura("AF145FL", heladeraDeRetiro));
        }
    }

    @Nested
    @DisplayName("Una vez apoyada la tarjeta en el lector, la cantidad de usos es descontada o no, segun el caso")
    class TestDeChequeoDeTarjetaPostApertura{

        private int consultarUsos() throws Exception {
            // Tecnica de metaprogramacion para no hacer un getter en la clase
            Field cantUsosRestantes = personaSituacionVulnerable.getVinculacion().getClass().getDeclaredField("cantUsosRestantesPorDia");
            cantUsosRestantes.setAccessible(true);
            return (int) cantUsosRestantes.get(personaSituacionVulnerable.getVinculacion());
        }

        @Disabled("Este falla: Necesitamos atajar el error que salta en este caso")
        @Test
        @DisplayName("Como no habia viandas en la heladera, la persona no pierde un uso")
        public void testCantDeUsosPostApertura2() throws Exception {
            heladeraDeRetiro.retirarViandas(heladeraDeRetiro.getViandasEnStock().size()); // -> Vaciamos la heladera
            gestorDeAccesosAHeladeras.autorizarApertura("AG780EX", heladeraDeRetiro);
            assertEquals(8, consultarUsos());
        }

        @Test
        @DisplayName("Al avanzar un dia, la cantidad de usos se resetea")
        public void testCantDeUsosPostApertura3() throws Exception {
            LocalDate fechaDeHoy = LocalDate.now();
            LocalDate fechaDeManiana = fechaDeHoy.plusDays(1);

            // Usamos un Mock para simular un Clock y poder avanzar un dia
            try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
                mockedLocalDate.when(LocalDate::now).thenReturn(fechaDeHoy);

                for (int i = 0; i < 8; i++) {
                    gestorDeAccesosAHeladeras.autorizarApertura("AG780EX", heladeraDeRetiro);
                }
                assertEquals(0, consultarUsos());

                mockedLocalDate.when(LocalDate::now).thenReturn(fechaDeManiana);
                gestorDeAccesosAHeladeras.autorizarApertura("AG780EX", heladeraDeRetiro);
                assertEquals(7, consultarUsos());
            }

        }

    }
}
