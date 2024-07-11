package tp_anual.tp_anual_implementacion;


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
    @BeforeEach
    void setUp() {
        Documento documento1 = new Documento(DNI, "12345678", MASCULINO);
        Direccion direccion1 = new Direccion("Medrano", "500", "1200", "5A");
        Persona persona1 = new PersonaHumana("Juan", "Gomez", null, documento1, direccion1);
        Colaborador colaborador1 = new Colaborador(persona1);
        Sistema.getInstancia().darDeAltaColaborador(colaborador1);

        Documento documento2 = new Documento(LC, "5555678", null);
        Direccion direccion2 = new Direccion("Mozart", "50", "1010", "7B");
        Persona persona2 = new PersonaHumana("María", "Sarmiento", null, null, direccion2);
        Colaborador colaborador2 = new Colaborador(persona2);
        CorreoElectronico mail2 = new CorreoElectronico("masarmiento@gmail.com.ar");
        colaborador2.agregarMedioDeContacto(mail2);
        Sistema.getInstancia().darDeAltaColaborador(colaborador2);
    }

    @Test
    void Validaciones() {
        assertTrue(Sistema.getInstancia().existeColaborador(colaborador1), "Verifica la existencia del colaborador en el sistema");
        assertFalse(Sistema.getInstancia().existeColaborador(colaborador2), "Verifica la inexistencia del colaborador en el sistema");
    }
}