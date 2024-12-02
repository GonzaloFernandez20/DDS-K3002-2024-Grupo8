package TestUnitarios;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Repositorios.RepositorioIncidentes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRepositorioIncidentes {

    Heladera heladera;
    FallaTecnica fallaTecnica;
    Colaborador colaboradorHumano;

    void setUp() {
        RepositorioIncidentes.getInstancia().limpiarInstancia();

        Direccion direccion = new Direccion("Beauchef", "500");
        Documento documento = new Documento(TipoDeDocumento.DNI, "40.400.400", Sexo.FEMENINO);
        PersonaHumana personaHumana = new PersonaHumana("Juana", "Gonzalez", LocalDate.now().minusYears(25), documento, direccion);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        WhatsApp unMedio = new WhatsApp("15 2300-2950");
        mediosDeContacto.add(unMedio);
        colaboradorHumano = new Colaborador(personaHumana, mediosDeContacto);

        heladera = new Heladera(new Colaborador(new PersonaJuridica("Mini Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Beauchef", "500")), List.of(new WhatsApp("15 2300-2950"))), new Ubicacion(new Direccion("Beauchef", "500"), "CABA", "Mini Gastronomos Argentinos 1"), 30, new Modelo(20, -20), LocalDate.now().minusYears(1));
        fallaTecnica = new FallaTecnica(colaboradorHumano, "Se le desconectaron las neuronas.", heladera, null);
    }

    @Test
    void ValidarFallasTecnicasDeUnPeriodo() {
        setUp();
        RepositorioIncidentes.getInstancia().sumarIncidente(fallaTecnica);
        List<FallaTecnica> fallasTecnicas = RepositorioIncidentes.getInstancia().getFallasTecnicasDeHeladeraEntreFechas(heladera, LocalDate.now().minusWeeks(1), LocalDate.now());

        assertTrue(fallasTecnicas.stream().anyMatch(falla -> falla.getHeladeraDondeOcurrio().getIdHeladera() == heladera.getIdHeladera()
            && falla.getEstado() == EstadoDelIncidente.PENDIENTE
            && falla.getVisitas().isEmpty()
            && falla.getDescripcion().equals("Se le desconectaron las neuronas.")
            && falla.getMomentoDelSuceso() == fallaTecnica.getMomentoDelSuceso()
            && falla.getLinkFoto() == null
            && falla.getColaboradorInformante() == colaboradorHumano));
    }
}
