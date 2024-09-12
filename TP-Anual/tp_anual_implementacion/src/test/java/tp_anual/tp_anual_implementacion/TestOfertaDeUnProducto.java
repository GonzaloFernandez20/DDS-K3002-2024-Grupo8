package tp_anual.tp_anual_implementacion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.contribucion.Producto;
import Modelo.Dominio.localizacion.Direccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import Modelo.Dominio.persona.Persona;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.sistema.Sistema;

import java.time.LocalDate;

import static Modelo.Dominio.contribucion.Rubro.GASTRONOMIA;
import static org.junit.jupiter.api.Assertions.*;
import static Modelo.Dominio.persona.TipoOrganizacion.empresa;

@SpringBootTest
public class TestOfertaDeUnProducto {
/*
    Sistema sistema;

    CorreoElectronico mail1;
    Persona persona1;
    Colaborador colaborador1;

    Direccion direccion2;
    Persona persona2;
    Colaborador colaborador2;

    OfertaDeUnProducto oferta1;
    OfertaDeUnProducto oferta2;

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

        oferta1 = new OfertaDeUnProducto(colaborador1, LocalDate.of(2024, 6, 20), "Súper Cacerola", GASTRONOMIA, 100, "cacerola.jpg", producto1);
        oferta2 = new OfertaDeUnProducto(colaborador2, LocalDate.of(2024, 5, 8), "Cacerola-3000", GASTRONOMIA, 50, "cacerola.jpg", producto1);

        oferta2.procesarLaContribucion();
    }

    @Test
    void ValidacionesProcesarContribucion() throws ColaboracionInvalida {
        assertThrows(ColaboracionInvalida.class, () -> { oferta1.procesarLaContribucion(); }, "Para ofrecer productos debés se una persona JURIDICA");
        assertTrue(sistema.getOfertas().contains(oferta2), "El sistema  posee la nueva oferta");
    }*/
}