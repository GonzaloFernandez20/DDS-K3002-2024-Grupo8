package TestDeIntegracion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Modelo.Dominio.reportes.GestorDeReportes;
import Repositorios.RepositorioIncidentes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGenerarReportesSemanales {

    FallaTecnica fallaTecnica;

    @BeforeEach
    void setUp() {
        Direccion direccion = new Direccion("Saraza", "1200", "1234");
        Documento documento = new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO);
        PersonaHumana personaHumana = new PersonaHumana("Luis", "Gómez", LocalDate.now().minusYears(23), documento, direccion);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        WhatsApp unMedio = new WhatsApp("15 2350-2350");
        mediosDeContacto.add(unMedio);
        Colaborador colaboradorHumano = new Colaborador(personaHumana, mediosDeContacto);

        PersonaJuridica personaJuridica = new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", direccion);
        Colaborador colaboradorJuridico = new Colaborador(personaJuridica, mediosDeContacto);

        Ubicacion ubicacion = new Ubicacion(direccion, "CABA", "Gastronomos Argentinos 1");
        Modelo modelo = new Modelo(20, -20);

        Heladera heladera = new Heladera(colaboradorJuridico, ubicacion, 30, modelo, LocalDate.now().minusYears(1));

        FallaTecnica fallaTecnica = new FallaTecnica(colaboradorHumano, "Se le quemó un foquito.", heladera, null);

        RepositorioIncidentes.getInstancia().sumarIncidente(fallaTecnica);

        GestorDeReportes.getInstancia().generarReportesSemanales();
    }

    @Test
    void ValidacionCompletarReporteDeFallas() {
        assertTrue(GestorDeReportes.getInstancia().getReportes().contains(fallaTecnica));
    }
}
