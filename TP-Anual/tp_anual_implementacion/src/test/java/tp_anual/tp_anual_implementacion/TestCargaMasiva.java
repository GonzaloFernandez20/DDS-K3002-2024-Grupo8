package tp_anual.tp_anual_implementacion;

import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.carga_masiva.CargaMasiva;
import Modelo.Dominio.colaborador.Colaborador;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import Modelo.Dominio.documentacion.Documento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.sistema.Sistema;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Modelo.Dominio.documentacion.TipoDeDocumento.DNI;
import static Modelo.Dominio.documentacion.TipoDeDocumento.LE;
import static Modelo.Dominio.documentacion.TipoDeDocumento.LC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestCargaMasiva {

    Sistema sistema;
    CargaMasiva cargaMasiva;

    Mail mail2;
    List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
    PersonaHumana persona2;
    Colaborador colaborador2;
    Documento documentoPrueba;

    @BeforeEach
    void setUp() {
        sistema = Sistema.getInstancia();

        mail2 = new Mail("carlitosgonzalez@yahoo.com");
        mediosDeContacto.add(mail2);
        persona2 = new PersonaHumana("Carlos", "González", null, null, null);
        colaborador2 = new Colaborador(persona2,mediosDeContacto);
        sistema.darDeAltaColaborador(colaborador2);

        documentoPrueba = new Documento(DNI, "12345678", null);

        cargaMasiva = new CargaMasiva(getClass().getResource("/CSVCorrecto.csv").getPath());
    }

    @Test
    void ValidacionesPresenciaEnElSistema() {
        // setUp();
        cargaMasiva.migrar();
        // assertTrue(sistema.existeColaborador(new Colaborador(new PersonaHumana("Ana", "Días", null, new Documento(DNI, "40555555", null), null))), "El colaborador de la primera linea del CSV existe.");

        assertTrue(sistema.existeColaborador(colaborador2), "El colaborador de la segunda linea del CSV existe");
        assertTrue(sistema.buscarColaborador(colaborador2).tieneDocumentoSegunNumeroYTipo(documentoPrueba), "Un colaborador existente en el sistema tiene datos nuevos gracias a la migracion.");

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
