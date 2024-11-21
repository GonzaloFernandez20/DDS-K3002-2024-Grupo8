package TestUnitarios;

import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaColaborar;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.INGRESAR_VIANDAS_DONADAS;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestApertura {

    PermisoDeAperturaParaColaborar apertura;

    @BeforeEach
    void setUp() {
        Heladera heladera = new Heladera(new Colaborador(new PersonaJuridica("Mini Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Beauchef", "500", "2020")), List.of(new WhatsApp("15 2300-2950"))), new Ubicacion(new Direccion("Beauchef", "500", "2020"), "CABA", "Mini Gastronomos Argentinos 1"), 30, new Modelo(20, -20), LocalDate.now().minusYears(1));

        apertura = new PermisoDeAperturaParaColaborar(heladera, INGRESAR_VIANDAS_DONADAS);
    }

    /*@Test
    void ValidarQueEstaEnFechaSiEsUnaAperturaDeHoy() {
        assertTrue(apertura.aperturaParaEntregaDeDonacionEntre(LocalDate.now().minusWeeks(1), LocalDate.now()));
    }*/
}
