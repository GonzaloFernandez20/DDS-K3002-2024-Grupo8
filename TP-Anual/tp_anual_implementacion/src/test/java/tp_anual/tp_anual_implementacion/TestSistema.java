package tp_anual.tp_anual_implementacion;


import colaborador.Colaborador;
import documentacion.Documento;
import localizacion.Direccion;
import medios_de_contacto.CorreoElectronico;
import medios_de_contacto.MedioDeContacto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import persona.Persona;
import persona.PersonaHumana;
import sistema.Sistema;
import java.time.LocalDate;

import java.util.ArrayList;

import static documentacion.Sexo.FEMENINO;
import static documentacion.Sexo.MASCULINO;
import static documentacion.TipoDeDocumento.DNI;
import static documentacion.TipoDeDocumento.LC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestsSistema {
    Documento documento1;
    Direccion direccion1;
    Persona     persona1;
    Colaborador colaborador1;

    Documento documento2;
    Direccion direccion2;
    Persona persona2;
    Colaborador colaborador2;
    CorreoElectronico mail2;

    Documento documento3;
    Persona persona3;
    Colaborador colaborador3;
    Documento documento3Mock;
    Persona persona3Mock;
    Colaborador colaborador3Mock;

    Sistema sistema;

    @BeforeEach
    void setUp() {
        sistema = Sistema.getInstancia();

        documento1 = new Documento(DNI, "12345678", MASCULINO);
        direccion1 = new Direccion("Medrano", "500", "1200", "5A");
        persona1 = new PersonaHumana("Juan", "Gomez", null, documento1, direccion1);
        colaborador1 = new Colaborador(persona1);
        sistema.darDeAltaColaborador(colaborador1);

        documento2 = new Documento(LC, "5555678", null);
        direccion2 = new Direccion("Mozart", "50", "1010", "7B");
        persona2 = new PersonaHumana("María", "Sarmiento", null, null, direccion2);
        colaborador2 = new Colaborador(persona2);
        mail2 = new CorreoElectronico("masarmiento@gmail.com.ar");
        // colaborador1.agregarMedioDeContacto(new MedioDeContacto("jajaja@gmail.com"));
        colaborador2.agregarMedioDeContacto(mail2);
        // Sistema.getInstancia().darDeAltaColaborador(colaborador2);

        documento3 = new Documento(DNI, "40555555", FEMENINO);
        persona3 = new PersonaHumana("Ana", "Días", LocalDate.of(1999, 9, 01), documento3, direccion2);
        colaborador3 = new Colaborador(persona3);
        sistema.darDeAltaColaborador(colaborador3);

        documento3Mock = new Documento(DNI, "40555555", null);
        persona3Mock = new PersonaHumana("Ana", "Días", null, documento3Mock, null);
        colaborador3Mock = new Colaborador(persona3Mock);
    }

    @Test
    void ValidacionesExistenciaDeColaborador() {
        assertTrue(sistema.existeColaborador(colaborador1), "Verifica la existencia del colaborador en el sistema");
        assertFalse(sistema.existeColaborador(colaborador2), "Verifica la inexistencia del colaborador en el sistema");
    }

    @Test
    void ValidacionesBusquedaDeColaborador() {
        assertSame(sistema.buscarColaborador(colaborador1), colaborador1);
        assertNull(sistema.buscarColaborador(colaborador2), "No encuentra el colaborador en el sistema.");
        assertSame(sistema.buscarColaborador(colaborador3Mock), colaborador3);
    }

    @Test
    void ValidacionMonto() {
        sistema.agregarMonto(1550);
        assertSame(sistema.getMonto(), 1550.0);
    }
}