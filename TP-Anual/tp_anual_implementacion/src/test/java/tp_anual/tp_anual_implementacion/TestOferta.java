package tp_anual.tp_anual_implementacion;

import colaborador.Colaborador;
import contribucion.OfertaDeProductos;
import contribucion.Producto;
import documentacion.Documento;
import localizacion.Direccion;
import medios_de_contacto.CorreoElectronico;
import nuestras_excepciones.ColaboracionInvalida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import persona.Persona;
import persona.PersonaHumana;
import persona.PersonaJuridica;
import sistema.Sistema;

import java.time.LocalDate;

import static contribucion.Rubro.GASTRONOMIA;
import static documentacion.TipoDeDocumento.DNI;
import static org.junit.jupiter.api.Assertions.*;
import static persona.TipoOrganizacion.empresa;

@SpringBootTest
class testsOfertaDeProductos {

    Sistema sistema;

    CorreoElectronico mail1;
    Persona persona1;
    Colaborador colaborador1;

    Direccion direccion2;
    Persona persona2;
    Colaborador colaborador2;

    OfertaDeProductos oferta1;
    OfertaDeProductos oferta2;

    Producto producto1;

    @BeforeEach
    void setUp() throws ColaboracionInvalida {
        sistema = Sistema.getInstancia();

        mail1 = new CorreoElectronico("anadias30@gmail.com");
        persona1 = new PersonaHumana("Ana", "Días", null, null, null);
        colaborador1 = new Colaborador(persona1);
        colaborador1.agregarMedioDeContacto(mail1);
        sistema.darDeAltaColaborador(colaborador1);

        direccion2 = new Direccion("Corrientes", "3000", "1390", "13D");
        persona2 = new PersonaJuridica("Mundo Gastronomico", empresa, "Gastronomia", direccion2);
        colaborador2 = new Colaborador(persona2);
        sistema.darDeAltaColaborador(colaborador2);

        producto1 = new Producto("Cacerola", 100);

        oferta1 = new OfertaDeProductos(colaborador1, LocalDate.of(2024, 6, 20), "Súper Cacerola", GASTRONOMIA, 100, "cacerola.jpg", producto1);
        oferta2 = new OfertaDeProductos(colaborador2, LocalDate.of(2024, 5, 8), "Cacerola-3000", GASTRONOMIA, 50, "cacerola.jpg", producto1);

        oferta2.procesarContribucion();
    }

    @Test
    void ValidacionesProcesarContribucion() throws ColaboracionInvalida {
        assertThrows(ColaboracionInvalida.class, () -> { oferta1.procesarContribucion(); }, "Para ofrecer productos debés se una persona JURIDICA");
        assertTrue(sistema.getOfertas().contains(oferta2), "El sistema  posee la nueva oferta");
    }
}