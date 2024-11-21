package TestDeIntegracion;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaColaborar;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeVianda;
import Modelo.Dominio.contribucion.Vianda;
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
import Modelo.Dominio.sistema.Sistema;
import Repositorios.RepositorioAperturas;
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

import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.INGRESAR_VIANDAS_DONADAS;
import static org.junit.jupiter.api.Assertions.*;

public class TestGenerarReportesSemanales {
    FallaTecnica fallaTecnica;
    ReporteDeFallas reporteDeFallas;
    Heladera heladera;
    ReporteDeViandasPorColaborador reporteDeViandasPorColaborador;
    Colaborador colaboradorHumano;
    ReporteDeViandasPorHeladera reporteDeViandasPorHeladera;

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
        colaboradorHumano = new Colaborador(personaHumana, mediosDeContacto);
        colaboradorHumano.setId_colaborador(999);

        Sistema.getInstancia().darDeAltaColaborador(colaboradorHumano);

        heladera = new Heladera(new Colaborador(new PersonaJuridica("Mini Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Beauchef", "500", "2020")), List.of(new WhatsApp("15 2300-2950"))), new Ubicacion(new Direccion("Beauchef", "500", "2020"), "CABA", "Mini Gastronomos Argentinos 1"), 30, new Modelo(20, -20), LocalDate.now().minusYears(1));

        RepositorioHeladeras.getInstancia().agregarHeladera(heladera);

        fallaTecnica = new FallaTecnica(colaboradorHumano, "Se le quemó un foquito.", heladera, null);

        reporteDeFallas = new ReporteDeFallas(LocalDate.now());
        reporteDeViandasPorColaborador = new ReporteDeViandasPorColaborador(LocalDate.now());
        reporteDeViandasPorHeladera = new ReporteDeViandasPorHeladera(LocalDate.now());
    }

    @Test
    void ValidacionCompletarReporteDeFallaTecnica() {
        RepositorioIncidentes.getInstancia().sumarIncidente(fallaTecnica);

        reporteDeFallas.completarReporte();

        String path = "src/main/resources/static/reportes/ReporteDeFallas.pdf";

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
            assertTrue(false);
        }
    }

    @Test
    void ValidacionCompletarReportesSemanales() {
        RepositorioIncidentes.getInstancia().sumarIncidente(fallaTecnica);

        AccesoDeColaborador accesoDeColaborador = new AccesoDeColaborador("TP89", colaboradorHumano);
        colaboradorHumano.setTarjeta(accesoDeColaborador);

        heladera.setIdHeladera(8888);

        Vianda vianda = new Vianda("Tortilla de Papa", LocalDate.now().plusDays(5), colaboradorHumano, heladera, null, null);
        DonacionDeVianda contribucionDeVianda = new DonacionDeVianda(colaboradorHumano, heladera, List.of(vianda), LocalDate.now());

        GestorDePermisosDeApertura.registrarMovimientoSolicitado(colaboradorHumano, INGRESAR_VIANDAS_DONADAS, contribucionDeVianda, heladera);
        accesoDeColaborador.aperturaAutorizada(heladera);

        PermisoDeAperturaParaColaborar apertura = new PermisoDeAperturaParaColaborar(heladera, INGRESAR_VIANDAS_DONADAS, contribucionDeVianda);
        RepositorioAperturas.getInstancia().agregarApertura(apertura);

        GestorDeReportes.getInstancia().generarReportesSemanales();

        List<FallasPorHeladera> fallasSemanales = GestorDeReportes.getInstancia().getReportesDeFallas().getLast().getFallasPorHeladera();
        FallasPorHeladera fallaDeEstaHeladera = new FallasPorHeladera(heladera, 1);
        assertTrue(laListaDeFallasPorHeladeraContieneLaFalla(fallasSemanales, fallaDeEstaHeladera));

        List<ViandasPorColaborador> viandasPorColaboradorSemanales = GestorDeReportes.getInstancia().getReportesDeViandasPorColaborador().getLast().getViandasPorColaborador();
        ViandasPorColaborador viandasPorColaborador = new ViandasPorColaborador(colaboradorHumano, 1);
        assertTrue(laListaDeViandasPorColaboradorContieneLasViandasPorColaborador(viandasPorColaboradorSemanales, viandasPorColaborador));

        List<ViandasPorHeladera> viandasPorHeladerasSemanales = GestorDeReportes.getInstancia().getReportesDeViandasPorHeladera().getLast().getViandasPorHeladeras();
        ViandasPorHeladera viandasPorHeladera = new ViandasPorHeladera(heladera, 0, 1);
        assertTrue(laListaDeViandasPorHeladerasContieneLasViandasPorHeladera(viandasPorHeladerasSemanales, viandasPorHeladera));
    }

    private boolean laListaDeFallasPorHeladeraContieneLaFalla(List<FallasPorHeladera> fallas, FallasPorHeladera falla) {
        for(int i=0; i<fallas.size(); i++) {
            if(fallas.get(i).getHeladera().getIdHeladera() == falla.getHeladera().getIdHeladera() && Objects.equals(fallas.get(i).getCantidadDeFallas(), falla.getCantidadDeFallas())) {
                return true;
            }
        }

        return false;
    }

    private boolean laListaDeViandasPorColaboradorContieneLasViandasPorColaborador (List<ViandasPorColaborador> viandas, ViandasPorColaborador vianda) {
        for(int i=0; i<viandas.size(); i++) {
            if(Objects.equals(viandas.get(i).getColaborador().getId_colaborador(), vianda.getColaborador().getId_colaborador()) && Objects.equals(viandas.get(i).getCantidadDeViandas(), vianda.getCantidadDeViandas())) {
                return true;
            }
        }

        return false;
    }

    private boolean laListaDeViandasPorHeladerasContieneLasViandasPorHeladera(List<ViandasPorHeladera> viandas, ViandasPorHeladera vianda) {
        for(int i=0; i<viandas.size(); i++) {
            if(Objects.equals(viandas.get(i).getHeladera().getIdHeladera(), vianda.getHeladera().getIdHeladera()) && viandas.get(i).getViandasColocadas() == vianda.getViandasColocadas() && viandas.get(i).getViandasRetiradas() == vianda.getViandasRetiradas()) {
                return true;
            }
        }

        return false;
    }
}
