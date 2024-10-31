package tp_anual.tp_anual_implementacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.seguridad.Validador;

import static org.junit.jupiter.api.Assertions.*;


class ValidadorTests {

    private Validador validador;

    @BeforeEach
    void setUp() {
        validador = Validador.getInstancia();
    }

    @Test
    void testFortalezaContrasenia() {
        setUp();
        // Verificar que lanza una excepción cuando la contraseña es insegura
        Exception exception = assertThrows(RuntimeException.class, () -> {
            validador.validarConstrasenia("1234567890");
        }, "No cumple criterio: Contrasenia insegura");
        assertEquals("Contrasenia demasiado debil, elija otra.", exception.getMessage());

        // Verificar que no lanza excepción cuando la contraseña es segura
        assertDoesNotThrow(() -> validador.validarConstrasenia("Riquelme10"), "Cumple criterio: Contrasenia segura");
    }

    @Test
    void testLongitudMinima() {
        setUp();
        // Verificar que lanza una excepción cuando la contraseña es demasiado corta
        Exception exceptionCorta = assertThrows(RuntimeException.class, () -> {
            validador.validarConstrasenia("3k94d6");
        }, "No cumple criterio: demasiado corta");
        assertEquals("La contraseña es demasiado corta. Debe tener más de 8 caracteres.", exceptionCorta.getMessage());

        // Verificar que lanza una excepción cuando la contraseña es demasiado larga
        Exception exceptionLarga = assertThrows(Exception.class, () -> {
            validador.validarConstrasenia("j5is75nk4jwbch4ihd98bdajncxiabfbqcnacbn3h4chbqiuea4n5g83ng58ngun3g");
        }, "No cumple criterio: demasiado larga");
        assertEquals("La contraseña es demasiado larga. No debe tener más de 64 caracteres.", exceptionLarga.getMessage());

        // Verificar que no lanza excepción cuando la contraseña cumple con los criterios de longitud
        assertDoesNotThrow(() -> validador.validarConstrasenia("j5is75nk4"), "Cumple criterio: Contrasenia segura");
    }
    @Test
    void testIntegracion() {
        // Probar el validador en el contexto de una aplicación integrada
        // Verificar interacciones con otros componentes del sistema
    }
}
