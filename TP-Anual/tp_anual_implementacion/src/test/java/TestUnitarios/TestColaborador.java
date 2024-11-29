package TestUnitarios;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura;
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
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Modelo.Dominio.sistema.Sistema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestColaborador {

    Colaborador colaborador;

    @BeforeEach
    void setUp() {
        Direccion direccion = new Direccion("Beauchef", "500", "2020");
        Documento documento = new Documento(TipoDeDocumento.DNI, "40.400.400", Sexo.FEMENINO);
        PersonaHumana personaHumana = new PersonaHumana("Juana", "Gonzalez", LocalDate.now().minusYears(25), documento, direccion);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        WhatsApp unMedio = new WhatsApp("15 2300-2950");
        mediosDeContacto.add(unMedio);
        colaborador = new Colaborador(personaHumana, mediosDeContacto);
        colaborador.setId_colaborador(999);

        Sistema.getInstancia().darDeAltaColaborador(colaborador);
    }

    @Disabled
    @Test
    void ValidarQueTengaLasDonacionesDeViandaDeHoy() {
        AccesoDeColaborador accesoDeColaborador = new AccesoDeColaborador("TP89", colaborador);
        colaborador.setTarjeta(accesoDeColaborador);

        Heladera heladera = new Heladera(new Colaborador(new PersonaJuridica("Mini Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Beauchef", "500", "2020")), List.of(new WhatsApp("15 2300-2950"))), new Ubicacion(new Direccion("Beauchef", "500", "2020"), "CABA", "Mini Gastronomos Argentinos 1"), 30, new Modelo(20, -20), LocalDate.now().minusYears(1));

        Vianda vianda = new Vianda("Tortilla de Papa", LocalDate.now().plusDays(5), colaborador, heladera, null, null);

        DonacionDeViandas contribucionDeVianda = new DonacionDeViandas(colaborador, heladera, List.of(vianda), LocalDate.now());

        GestorDePermisosDeApertura.registrarMovimientoSolicitado(colaborador, MotivoApertura.INGRESAR_VIANDAS_DONADAS, contribucionDeVianda, heladera);
        accesoDeColaborador.estaAutorizadaLaApertura(heladera);

        assertEquals(1, (int) colaborador.cantidadDeDonacionesDeViandaEntre(LocalDateTime.now().minusWeeks(1), LocalDateTime.now()));
    }
}
