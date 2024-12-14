package Modelo.Dominio.reportes;

import Config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GestorDeReportes {
    private List<ReporteSemanal> reportes = new ArrayList<ReporteSemanal>();
    private static GestorDeReportes instancia  = null;

    @Autowired
    AppConfig config;

    public static GestorDeReportes getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeReportes();
        }
        return instancia;
    }



    public void generarReportesSemanales(){
        ReporteDeFallas reporteDeFallas = config.reporteDeFallas();
        reporteDeFallas.setFechaDeCreacion(LocalDate.now());
        reporteDeFallas.completarReporte();

        ReporteDeViandasPorColaborador reporteDeViandasPorColaborador = config.reporteDeViandasPorColaborador();
        reporteDeViandasPorColaborador.setFechaDeCreacion(LocalDate.now());
        reporteDeViandasPorColaborador.completarReporte();

        ReporteDeViandasPorHeladera reporteDeViandasPorHeladera = config.reporteDeViandasPorHeladera();
        reporteDeViandasPorHeladera.setFechaDeCreacion(LocalDate.now());
        reporteDeViandasPorHeladera.completarReporte();

        reportes.add(reporteDeFallas);
        reportes.add(reporteDeViandasPorHeladera);
        reportes.add(reporteDeViandasPorColaborador);
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
