package tp_anual.tp_anual_implementacion;

import carga_masiva.CargaMasiva;
import colaborador.Colaborador;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import documentacion.Documento;
import documentacion.TipoDeDocumento;
import localizacion.Direccion;
import medios_de_contacto.CorreoElectronico;
import medios_de_contacto.MedioDeContacto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import persona.Persona;
import persona.PersonaHumana;
import sistema.Sistema;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static documentacion.Sexo.FEMENINO;
import static documentacion.Sexo.MASCULINO;
import static documentacion.TipoDeDocumento.DNI;
import static documentacion.TipoDeDocumento.LE;
import static documentacion.TipoDeDocumento.LC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestsCargaMasiva {

    Sistema sistema;
    CargaMasiva cargaMasiva;

    Documento documento1;
    PersonaHumana persona1;
    Colaborador colaborador1;

    CorreoElectronico mail2;
    PersonaHumana persona2;
    Colaborador colaborador2;
    Documento documentoPrueba;

    @BeforeEach
    void setUp() {
        sistema = Sistema.getInstancia();

        mail2 = new CorreoElectronico("carlitosgonzalez@yahoo.com");
        persona2 = new PersonaHumana("Carlos", "González", null, null, null);
        colaborador2 = new Colaborador(persona2);
        colaborador2.agregarMedioDeContacto(mail2);
        sistema.darDeAltaColaborador(colaborador2);

        cargaMasiva = new CargaMasiva(Objects.requireNonNull(getClass().getClassLoader().getResource("CSVCorrecto.csv")).getPath());
        cargaMasiva.migrar();

        documento1 = new Documento(DNI, "40555555", null);
        persona1 = new PersonaHumana("Ana", "Días", null, documento1, null);
        colaborador1 = new Colaborador(persona1);

        documentoPrueba = new Documento(DNI, "12345678", null);
    }

    @Test
    void ValidacionesPresenciaEnElSistema() {
        // setUp();
        assertTrue(sistema.existeColaborador(colaborador1), "El colaborador de la primera linea del CSV existe.");
        assertTrue(sistema.existeColaborador(colaborador2), "El colaborador de la segunda linea del CSV existe");
        assertTrue(sistema.buscarColaborador(colaborador2).tieneDocumentoSegunNumeroYTipo(documentoPrueba), "Un colaborador existente en el sistema tiene datos nuevos gracias a la migracion.");
        List<Colaborador> colaboradores = sistema.getColaboradores();
        assertSame(sistema.getColaboradores().size(), 4, "El sistema tiene la cantidad de colaboradores del CSV");
    }

    @Test
    void ValidacionesSonCeldasValidas() throws FileNotFoundException {
        try (CSVReader reader = new CSVReader(new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource("CSVCorrecto.csv")).getPath()))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                for (int i = 0; i < linea.length; i++) {
                    String campo = linea[i];
                    String[] partes = campo.split(";");
                    assertTrue(cargaMasiva.sonCeldasValidas(partes));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al abrir el archivo: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.err.println("Error de validación CSV: " + e.getMessage());
        }
    }

    @Test
    void ValidacionesCasteoTipoDeDocumento() {
        assertSame(cargaMasiva.castearTipoDoc("DNI"), DNI, "Castea correctamente al DNI");
        assertSame(cargaMasiva.castearTipoDoc("LC"), LC, "Castea correctamente a la LC");
        assertSame(cargaMasiva.castearTipoDoc("LE"), LE, "Castea correctamente a la LE");
        assertNull(cargaMasiva.castearTipoDoc(" DNI"), "No identifica un tipo de documento incorrecto");
    }

    @Test
    void ValidacionEsFechaValidaConFormato() {
        assertTrue(cargaMasiva.esFechaValida("14/06/2000"), "El formato de fecha es valido");
        assertFalse(cargaMasiva.esFechaValida("30/30/10"), "El formato de fecha es invalido");
        assertFalse(cargaMasiva.esFechaValida("20-09-2020"), "El formato de fecha es invalido");
    }
}
