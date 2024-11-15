package Modelo.Dominio.reportes;

import Modelo.Dominio.reportes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorDeReportes {
    private List<ReporteSemanal> reportes;
    private static GestorDeReportes instancia  = null;

    public static GestorDeReportes getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeReportes();
        }
        return instancia;
    }

    public void generarReportesSemanales(){
        /*Proceso calendarizado que hace todos los cálculos necesarios*/
        ReporteDeFallas reporteDeFallas = new ReporteDeFallas(LocalDate.now());
        ReporteDeViandasPorColaborador reporteDeViandasPorColaborador = new ReporteDeViandasPorColaborador(LocalDate.now());
        ReporteDeViandasPorHeladera reporteDeViandasPorHeladera = new ReporteDeViandasPorHeladera(LocalDate.now());

        reporteDeFallas.completarReporte();
        reporteDeViandasPorColaborador.completarReporte();
        reporteDeViandasPorHeladera.completarReporte();

        verificarExistenciaDeReportes();

        reportes.add(reporteDeFallas);
        reportes.add(reporteDeViandasPorHeladera);
        reportes.add(reporteDeViandasPorColaborador);
    }

    private void verificarExistenciaDeReportes() {
        if(reportes == null) {
            reportes = new ArrayList<>();
        }
    }

    public void limpiarInstancia() {
        instancia = null;
    }

    public List<ReporteSemanal> getReportes() { return reportes; }

    public List<ReporteDeFallas> getReportesDeFallas() { return reportes.stream().filter(reporte -> reporte instanceof ReporteDeFallas).map(reporte -> (ReporteDeFallas) reporte).toList(); }
}
