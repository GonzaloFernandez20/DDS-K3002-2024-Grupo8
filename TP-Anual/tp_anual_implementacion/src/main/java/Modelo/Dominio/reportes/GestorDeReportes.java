package reportes;

import heladera.Heladera;
import sistema.Sistema;

import java.time.LocalDate;
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
        reportes.add(reporteDeFallas);
        reportes.add(reporteDeViandasPorHeladera);
        reportes.add(reporteDeViandasPorColaborador);
    }
}
