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
import Modelo.Dominio.reportes.*;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestGenerarReportesSemanales {
    FallaTecnica fallaTecnica;
    ReporteDeFallas reporteDeFallas;
    Heladera heladera;

    @BeforeEach
    void setUp() {
        GestorDeReportes.getInstancia().limpiarInstancia();
        RepositorioIncidentes.getInstancia().limpiarInstancia();

        Direccion direccion = new Direccion("Beauchef", "500", "2020");
        Documento documento = new Documento(TipoDeDocumento.DNI, "40.400.400", Sexo.FEMENINO);
        PersonaHumana personaHumana = new PersonaHumana("Juana", "Gonzalez", LocalDate.now().minusYears(25), documento, direccion);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        WhatsApp unMedio = new WhatsApp("15 2300-2950");
        mediosDeContacto.add(unMedio);
        Colaborador colaboradorHumano = new Colaborador(personaHumana, mediosDeContacto);

        heladera = new Heladera(new Colaborador(new PersonaJuridica("Mini Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Beauchef", "500", "2020")), List.of(new WhatsApp("15 2300-2950"))), new Ubicacion(new Direccion("Beauchef", "500", "2020"), "CABA", "Mini Gastronomos Argentinos 1"), 30, new Modelo(20, -20), LocalDate.now().minusYears(1));

        RepositorioHeladeras.getInstancia().agregarHeladera(heladera);

        fallaTecnica = new FallaTecnica(colaboradorHumano, "Se le quemó un foquito.", heladera, null);

        reporteDeFallas = new ReporteDeFallas(LocalDate.now());
        reporteDeFallas.sumarFallasPorheladera(new FallasPorHeladera(heladera, 1));
    }

    @Test
    void ValidacionCompletarReporteDeFallaTecnica() {
        RepositorioIncidentes.getInstancia().sumarIncidente(fallaTecnica);

        reporteDeFallas.completarReporte();

        String path = "src/main/resources/reportes/ReporteDeFallas.pdf";

        File pdfFile = new File(path);
        assertTrue(pdfFile.exists() && pdfFile.length() > 0, "No se encontró el PDF.");

        try {
            PdfReader reader = new PdfReader(path);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                String pageContent = PdfTextExtractor.getTextFromPage(reader, i);
                assertTrue(pageContent.contains("Mini Gastronomos Argentinos"), "No se encontró la heladera ingresada en el PDF.");
            }
            reader.close();
        } catch (IOException e) {

        }
    }

    @Test
    void ValidacionCompletarReportesSemanales() {
        RepositorioIncidentes.getInstancia().sumarIncidente(fallaTecnica);
        GestorDeReportes.getInstancia().generarReportesSemanales();

        try {
            List<FallasPorHeladera> fallasSemanales = GestorDeReportes.getInstancia().getReportesDeFallas().getLast().getFallasPorHeladera();

            FallasPorHeladera fallaDeEstaHeladera = new FallasPorHeladera(heladera, 1);

            assertTrue(laListaDeFallasPorHeladeraContieneLaFalla(fallasSemanales, fallaDeEstaHeladera));
        } catch (Exception e){
            assertTrue(false, "No hay fallas.");
        }
    }

    private boolean laListaDeFallasPorHeladeraContieneLaFalla(List<FallasPorHeladera> lista1, FallasPorHeladera falla) {
        for(int i=0; i<lista1.size(); i++) {
            if(lista1.get(i).getHeladera().getIdHeladera() == falla.getHeladera().getIdHeladera() && Objects.equals(lista1.get(i).getCantidadDeFallas(), falla.getCantidadDeFallas())) {
                return true;
            }
        }

        return false;
    }
}
