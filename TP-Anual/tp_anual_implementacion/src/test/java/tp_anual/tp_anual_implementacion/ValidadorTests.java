package tp_anual.tp_anual_implementacion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.seguridad.Validador;


class ValidadorTests {

    private Validador validador;

    @BeforeEach
    void setUp() {
        validador = Validador.getInstancia();
    }

    @Test
    void testFortalezaContrasenia() {
        setUp();
        assertFalse(validador.validarConstrasenia("1234"), "No cumple criterio: Contrasenia insegura");
        assertTrue(validador.validarConstrasenia("Riquelme10"), "Cumple criterio: Contrasenia segura");
    }
    @Test
    void testLongitudMinima(){
        setUp();
        assertFalse(validador.validarConstrasenia("3k94d6"), "No cumple criterio: demasiado corta");
        assertFalse(validador.validarConstrasenia("j5is75nk4jwbch4ihd98bdajncxiabfbqcnacbn3h4chbqiuea4n5g83ng58ngun3g"), "No cumple criterio: demasiado larga");
        assertTrue(validador.validarConstrasenia("j5is75nk4"), "Cumple criterio: Contrasenia segura");
    }

    @Test
    void testIntegracion() {
        // Probar el validador en el contexto de una aplicación integrada
        // Verificar interacciones con otros componentes del sistema
    }
}
