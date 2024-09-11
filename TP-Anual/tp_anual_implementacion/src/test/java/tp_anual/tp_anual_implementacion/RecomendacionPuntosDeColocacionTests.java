package tp_anual.tp_anual_implementacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;

@SpringBootTest
class RecomendacionPuntosDeColocacionTests {
    private double latitud;
    private double longitud;
    private int radio;
    HacerseCargoDeHeladera contribucion = new HacerseCargoDeHeladera(null, null);

    @BeforeEach
    void setUp1() {
        latitud = 39.7749;
        longitud = -120.4194;
        radio = 7000;
    }
    @BeforeEach
    void setUp2() {
        latitud = 37.7749;
        longitud = -122.4194;
        radio = 5000;
    }

    @Test
    void testObtenerValores1() throws IOException {
        setUp1();
        contribucion.consultarRecomendaciones(latitud, longitud, radio);
        // Queda Chequear lo que sale por pantalla nomas...
    }
    @Test
    void testObtenerValores2() throws IOException{
        setUp2();
        contribucion.consultarRecomendaciones(latitud, longitud, radio);
    }

    @Test
    void testIntegracion() {
        // Probar el validador en el contexto de una aplicación integrada
        // Verificar interacciones con otros componentes del sistema
    }
}