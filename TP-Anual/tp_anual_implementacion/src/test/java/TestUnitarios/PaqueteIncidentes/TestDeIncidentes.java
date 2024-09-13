package TestUnitarios.PaqueteIncidentes;

import FactoryInstanciasParaTests.FactoryInstanciasParaTests;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.*;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Dominio.tecnico.Tecnico;
import Repositorios.RepositorioIncidentes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static Modelo.Dominio.incidentes.EstadoDelIncidente.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas relacionadas con los incidentes:")
public class TestDeIncidentes {

    FallaTecnica fallaTecnica;
    Alerta alerta;
    Heladera heladera;
    Tecnico tecnico;

    // <---------------------- Configuraciones ----------------------> //

    @BeforeEach
    public void configuracionInicial(){
        inicializarHeladera();
        fallaTecnica = new FallaTecnica(null, "Falla en la visagra", heladera, null);
        alerta = new Alerta(TipoAlerta.TEMPERATURA, heladera);
    }

     void inicializarHeladera(){
        heladera = FactoryInstanciasParaTests.instanciarOtraHeladera();
    }

    // <---------------------- Testeos ----------------------> //

    @Nested
    @DisplayName("Ocurre un incidente en una heladera")
    class TestSobreCreacionDeIncidentes{

        @Test
        @DisplayName("La informacion obtenida acerca de una Falla Tecnica es correcta")
        public void testDeInformacionDeFalla(){
            String infoDeLaFalla = fallaTecnica.obtenerInformacion();
            String infoEsperadaDeLaFalla = "se produjo una Falla Tecnica: \n" + "Descripcion: " + "Falla en la visagra" + "\n" + "Link Foto: " + null;
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
            assertAll(
                    () -> assertEquals(PENDIENTE, fallaTecnica.getEstado()),
                    () -> assertEquals(PENDIENTE, alerta.getEstado()) );
        }
    }
    // <--------------------------------------------------------------> //
    @Nested
    @DisplayName("Un Tecnico acude a una heladera ante una Falla Tecnica o Alerta: ")
    class TestSobreVisitaTecnica {
        // -------- Entorno necesario
        @BeforeEach
        public void instanciarTecnico() {
            tecnico = new Tecnico(null, null, null, null);
        }
        // --------

        @Test
        @DisplayName("El estado del incidente coincide con el estado indicado por el tecnico en la visita")
        public void testDeEstadoLuegoDeVisitaTecnicaExitosa() {

            // ----- El tecnico acude a la heladera
            VisitaTecnica visitaDeFallaTecnica = new VisitaTecnica(NO_SOLUCIONADO, "Comprar una nueva visagra", tecnico, null, fallaTecnica);
            VisitaTecnica visitaDeAlerta = new VisitaTecnica(SOLUCIONADO, "La heladera quedo abierta", tecnico, null, alerta);
            // -----
            fallaTecnica.registrarVisita(visitaDeFallaTecnica);
            alerta.registrarVisita(visitaDeAlerta);

            assertAll("Verificacion de estado de los incidentes luego de una visita",
                    () -> assertEquals(NO_SOLUCIONADO, fallaTecnica.getEstado()),
                    () -> assertEquals(SOLUCIONADO, alerta.getEstado()) );

            assertAll("La visita quedo en el registro del incidente",
                    () -> assertTrue(fallaTecnica.getVisitas().contains(visitaDeFallaTecnica)),
                    () -> assertTrue(alerta.getVisitas().contains(visitaDeAlerta)));
        }

        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @Nested
        @DisplayName("El técnico puede hacer varias visitas hasta solucionar el incidente y verificar visitas")
        class TestDeIteracionDeVisitas{
            @Test
            @DisplayName("Todas las visitas fueron registradas")
            public void testDeIteracionDeVisitasYChequeo() {
                visitasTecnicas().forEach(arguments -> {
                    VisitaTecnica visitaTecnica = (VisitaTecnica) arguments.get()[0];
                    EstadoDelIncidente estadoEsperado = (EstadoDelIncidente) arguments.get()[1];
                    fallaTecnica.registrarVisita(visitaTecnica);

                    assertEquals(estadoEsperado, fallaTecnica.getEstado(),
                            "El estado de la falla técnica debería ser " + estadoEsperado); });

                assertEquals(3, fallaTecnica.getVisitas().size(), "Se deben registrar 3 visitas");
            }

            private Stream<Arguments> visitasTecnicas() {
                return Stream.of(
                        Arguments.of(new VisitaTecnica(NO_SOLUCIONADO, "Comprar una nueva visagra", tecnico, null, fallaTecnica), NO_SOLUCIONADO),
                        Arguments.of(new VisitaTecnica(NO_SOLUCIONADO, "La visagra era incorrecta", tecnico, null, fallaTecnica), NO_SOLUCIONADO),
                        Arguments.of(new VisitaTecnica(SOLUCIONADO, "Trabajo terminado", tecnico, null, fallaTecnica), SOLUCIONADO)
                );
            }
        }
    }
}