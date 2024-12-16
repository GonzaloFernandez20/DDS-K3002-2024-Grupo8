package Modelo.Dominio.reportes;

import ServiceImpl.ReportesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GestorDeReportes {
    private List<ReporteSemanal> reportes = new ArrayList<ReporteSemanal>();
    private static GestorDeReportes instancia  = null;

    public GestorDeReportes(){}

    public static GestorDeReportes getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeReportes();
        }
        return instancia;
    }

/*    public void generarReportesSemanales(){
//        ReporteDeFallas reporteDeFallas = new ReporteDeFallas();
        ReporteDeFallas reporteDeFallas = this.generarReporteDeFallasPorHeladera();
        reporteDeFallas.setFechaDeCreacion(LocalDate.now());
        reporteDeFallas.completarReporte();

        ReporteDeViandasPorColaborador reporteDeViandasPorColaborador = new ReporteDeViandasPorColaborador();
        reporteDeViandasPorColaborador.setFechaDeCreacion(LocalDate.now());
        reporteDeViandasPorColaborador.completarReporte();

        ReporteDeViandasPorHeladera reporteDeViandasPorHeladera = new ReporteDeViandasPorHeladera();
        reporteDeViandasPorHeladera.setFechaDeCreacion(LocalDate.now());
        reporteDeViandasPorHeladera.completarReporte();

        reportes.add(reporteDeFallas);
//        reportes.add(reporteDeViandasPorHeladera);
//        reportes.add(reporteDeViandasPorColaborador);
    }
    */
    public void generarReportesSemanales(List<ReporteSemanal> reportesSemanales){
        reportesSemanales.forEach(reporteSemanal -> {
            reporteSemanal.setFechaDeCreacion(LocalDate.now());
            reporteSemanal.completarReporte();
            reportes.add(reporteSemanal);
        });
    }

    private void verificarExistenciaDeReportes() {
        if(reportes == null) {
            reportes = new ArrayList<>();
        }
    }
    //generar reportes que sean administrados por SpringBoot
    @Bean
    public ReporteDeFallas generarReporteDeFallasPorHeladera(){
        return new ReporteDeFallas();
//        ReporteDeFallas reporteDeFallas = new ReporteDeFallas();
//        reporteDeFallas.setHeladeraRepository(heladeraRepository);
    }

    public void limpiarInstancia() {
        instancia = null;
    }

    public List<ReporteSemanal> getReportes() { return reportes; }
    public List<ReporteDeFallas> getReportesDeFallas() { return reportes.stream().filter(reporte -> reporte instanceof ReporteDeFallas).map(reporte -> (ReporteDeFallas) reporte).toList(); }
    public List<ReporteDeViandasPorColaborador> getReportesDeViandasPorColaborador() { return reportes.stream().filter(reporte -> reporte instanceof ReporteDeViandasPorColaborador).map(reporte -> (ReporteDeViandasPorColaborador) reporte).toList(); }
    public List<ReporteDeViandasPorHeladera> getReportesDeViandasPorHeladera() { return reportes.stream().filter(reporte -> reporte instanceof ReporteDeViandasPorHeladera).map(reporte -> (ReporteDeViandasPorHeladera) reporte).toList(); }
}
