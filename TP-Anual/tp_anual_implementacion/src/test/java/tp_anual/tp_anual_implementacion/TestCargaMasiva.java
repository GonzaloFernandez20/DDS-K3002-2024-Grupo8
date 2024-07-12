package tp_anual.tp_anual_implementacion;

import carga_masiva.CargaMasiva;
import colaborador.Colaborador;
import documentacion.Documento;
import localizacion.Direccion;
import medios_de_contacto.CorreoElectronico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import persona.Persona;
import persona.PersonaHumana;
import sistema.Sistema;

import java.util.ArrayList;
import java.util.List;

import static documentacion.Sexo.MASCULINO;
import static documentacion.TipoDeDocumento.DNI;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestsCargaMasiva {

    Sistema sistema;
    CargaMasiva cargaMasiva;

    Documento documento1;
    Persona persona1;
    Colaborador colaborador1;

    CorreoElectronico mail2;
    Persona persona2;
    Colaborador colaborador2;

    @BeforeEach
    void setUp() {
        sistema = Sistema.getInstancia();

        mail2 = new CorreoElectronico("carlitosgonzalez@yahoo.com");
        persona2 = new PersonaHumana("Carlos", "González", null, null, null);
        colaborador2 = new Colaborador(persona2);
        sistema.darDeAltaColaborador(colaborador2);

        cargaMasiva = new CargaMasiva(getClass().getClassLoader().getResource("CSVCorrecto.csv").getPath());
        cargaMasiva.migrar();

        documento1 = new Documento(DNI, "40555555", null);
        persona1 = new PersonaHumana("Ana", "Días", null, documento1, null);
        colaborador1 = new Colaborador(persona1);
    }

    @Test
    void ValidacionesPresenciaEnElSistema() {
        assertTrue(sistema.existeColaborador(colaborador1), "El colaborador de la primera linea del CSV existe.");
        assertTrue(colaborador2.tieneDocumentoSegunNumeroYTipo(new Documento(DNI, "12345678", null)), "Un colaborador existente en el sistema tiene datos nuvos gracias a la migracion.");
    }
}
