package TestUnitarios;

import Modelo.Dominio.Accesos_a_heladeras.AperturaConPermiso;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeViandas;
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
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.INGRESAR_VIANDAS_DONADAS;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestApertura {

    AperturaConPermiso apertura;

    @BeforeEach
    void setUp() {
        Heladera heladera = new Heladera(new Colaborador(new PersonaJuridica("Mini Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Beauchef", "500")), List.of(new WhatsApp("15 2300-2950"))), new Ubicacion(new Direccion("Beauchef", "500"), "CABA", "Mini Gastronomos Argentinos 1", null), 30, new Modelo(20, -20), LocalDate.now().minusYears(1));

        Direccion direccion = new Direccion("Beauchef", "500");
        Documento documento = new Documento(TipoDeDocumento.DNI, "40.400.400", Sexo.FEMENINO);
        PersonaHumana personaHumana = new PersonaHumana("Juana", "Gonzalez", LocalDate.now().minusYears(25), documento, direccion);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        WhatsApp unMedio = new WhatsApp("15 2300-2950");
        mediosDeContacto.add(unMedio);
        Colaborador colaborador = new Colaborador(personaHumana, mediosDeContacto);

        Vianda vianda = new Vianda("Fideos", LocalDate.now().plusWeeks(1), colaborador, heladera, null, null);

        DonacionDeViandas contribucion = new DonacionDeViandas(colaborador, heladera, List.of(vianda), LocalDate.now());

        apertura = new AperturaConPermiso(heladera, INGRESAR_VIANDAS_DONADAS, contribucion);
    }

    @Disabled
    @Test
    void ValidarQueEstaEnFechaSiEsUnaAperturaDeHoy() {
        assertTrue(apertura.aperturaParaEntregaDeDonacionEntre(LocalDateTime.now().minusWeeks(1), LocalDateTime.now()));
    }
}
