package tp_anual.tp_anual_implementacion;


import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import medios_de_contacto.CorreoElectronico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import Modelo.Dominio.persona.Persona;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.sistema.Sistema;
import java.time.LocalDate;

import static Modelo.Dominio.documentacion.Sexo.FEMENINO;
import static Modelo.Dominio.documentacion.Sexo.MASCULINO;
import static Modelo.Dominio.documentacion.TipoDeDocumento.DNI;
import static Modelo.Dominio.documentacion.TipoDeDocumento.LC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestsSistema {
    Documento documento1;
    Direccion direccion1;
    PersonaHumana     persona1;
    Colaborador colaborador1;

    Documento documento2;
    Direccion direccion2;
    PersonaHumana persona2;
    Colaborador colaborador2;
    CorreoElectronico mail2;

    Documento documento3;
    PersonaHumana persona3;
    Colaborador colaborador3;
    Documento documento3Mock;
    Persona persona3Mock;
    Colaborador colaborador3Mock;

    CorreoElectronico mail4;
    PersonaHumana persona4;
    Colaborador colaborador4;
    DonacionDeDinero contribucionDinero;

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
        colaborador2.agregarMedioDeContacto(mail2);

        documento3 = new Documento(DNI, "40555555", FEMENINO);
        persona3 = new PersonaHumana("Ana", "Días", LocalDate.of(1999, 9, 01), documento3, direccion2);
        colaborador3 = new Colaborador(persona3);
        sistema.darDeAltaColaborador(colaborador3);

        documento3Mock = new Documento(DNI, "40555555", null);
        persona3Mock = new PersonaHumana("Ana", "Días", null, documento3Mock, null);
        colaborador3Mock = new Colaborador(persona3Mock);

        mail4 = new CorreoElectronico("carlitosgonzalez@yahoo.com");
        persona4 = new PersonaHumana("Carlos", "González", null, null, null);
        colaborador4 = new Colaborador(persona4);
        colaborador4.agregarMedioDeContacto(mail4);
        sistema.darDeAltaColaborador(colaborador4);
        contribucionDinero = new DonacionDeDinero(colaborador4, LocalDate.of(2021, 9, 10), 1, null);
        colaborador4.sumarContribucion(contribucionDinero);
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
        assertSame(sistema.buscarColaborador(colaborador3), sistema.buscarColaborador(colaborador3Mock));
    }

    @Test
    void ValidacionMonto() {
        sistema.agregarMonto(1550);
        assertTrue(sistema.getMonto() == 1550.0, "El monto agregado es el esperado");
    }

    @Test
    void ValidacionActualizarPorCargaMasiva() {
        sistema.actualizarPorCargaMasiva(colaborador3Mock);
        assertSame(sistema.buscarColaborador(colaborador3Mock), sistema.buscarColaborador(colaborador3), "El colaborador es encontrado y sus datos son actualizados");
        sistema.actualizarPorCargaMasiva(colaborador4);
        assertTrue(sistema.existeColaborador(colaborador4), "Se da de alta al colaborador actualizado por carga masiva.");
    }
}