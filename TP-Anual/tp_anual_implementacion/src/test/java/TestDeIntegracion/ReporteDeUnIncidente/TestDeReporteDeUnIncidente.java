package TestDeIntegracion.ReporteDeUnIncidente;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Repositorios.RepositorioIncidentes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static Modelo.Dominio.heladera.EstadoHeladera.INACTIVA;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*  El objetivo de este test es probar el funcionamiento integral de todo lo referido
*   a reportar una falla, probando que cada uno de los componentes involucrados cumplan
*   con sus tareas respectivas
* */

@DisplayName("Prueba integral: Reporte de un incidente ")
public class TestDeReporteDeUnIncidente {
    FallaTecnica fallaTecnica;
    Heladera heladera;
    //Colaborador colaboradorInformante;
    //RepositorioIncidentes repositorioIncidentes;

    // <---------------------- Configuraciones ----------------------> //

    @BeforeEach
    public void configuracionInicial(){
        inicializarHeladera();
        fallaTecnica = new FallaTecnica(null, "Vidrio Roto", heladera, "foto.png");
        reportarUnIncidente();
    }

    private void inicializarHeladera(){
        heladera = new Heladera(null,
                new Ubicacion( new Direccion("Medrano", "981",null),"CABA", "Heladera Medrano UTN"),
                5,
                null,
                null);
        NotificadorDeSuscriptos notificadorHeladeraNueva = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorHeladeraNueva);
    }

    private void reportarUnIncidente(){
        GestorDeIncidentes.reportar(fallaTecnica);
    }
    // -> El incidente fue reportado, toca testear todo lo que pasa despues

    // <---------------------- Testeos ----------------------> //

    @Nested
    @DisplayName("incidente reportado: POV Heladera")
    class TestHeladeraRecibeElReporte{

        @Test
        @DisplayName("El estado de la heladera ahora es \"INACTIVA\"")
        public void TestHeladeraQuedoInactiva(){
            assertEquals(INACTIVA, heladera.getEstado());
        }
    }

    @Nested
    @DisplayName("incidente reportado: POV Tecnico")
    class Test2 {

        public void test1(){

        }
    }

    @Nested
    @DisplayName("incidente reportado: POV Repositorio de incidentes")
    class Test3 {

        public void test1(){

        }
    }



















}
