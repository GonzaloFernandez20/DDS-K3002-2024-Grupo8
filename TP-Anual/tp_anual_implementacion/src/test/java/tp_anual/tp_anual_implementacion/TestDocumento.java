package tp_anual.tp_anual_implementacion;

import documentacion.Documento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static documentacion.Sexo.FEMENINO;
import static documentacion.TipoDeDocumento.DNI;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TestDocumento {

    Documento documentoPrueba;
    Documento documentoPrueba2;

    @BeforeEach
    void setUp() {
        documentoPrueba = new Documento(DNI, "12345678", null);
        documentoPrueba2 = new Documento(DNI, "12345678", FEMENINO);
    }

    @Test
    void ValidacionesEsDocumentoSegunNumeroYTipo() {
        assertTrue(documentoPrueba.esDocumentoSegunNumeroYTipo(documentoPrueba));
        assertTrue(documentoPrueba.esDocumentoSegunNumeroYTipo(documentoPrueba2));
    }
}