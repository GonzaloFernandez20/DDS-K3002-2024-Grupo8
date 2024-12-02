package Modelo.Dominio.reportes;

import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.reportes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
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
/*        ReporteDeFallas reporteDeFallas = this.generarReporteDeFallasPorHeladera();//new ReporteDeFallas();
        reporteDeFallas.setFechaDeCreacion(LocalDate.now());
        reporteDeFallas.setHeladeraRepository(heladeraRepository);

//        ReporteDeViandasPorColaborador reporteDeViandasPorColaborador = new ReporteDeViandasPorColaborador(LocalDate.now());
//        ReporteDeViandasPorHeladera reporteDeViandasPorHeladera = new ReporteDeViandasPorHeladera(LocalDate.now());

        reporteDeFallas.completarReporte();
//        reporteDeViandasPorColaborador.completarReporte();
//        reporteDeViandasPorHeladera.completarReporte();

        verificarExistenciaDeReportes();

        reportes.add(reporteDeFallas);
//        reportes.add(reporteDeViandasPorHeladera);
//        reportes.add(reporteDeViandasPorColaborador);
    */}

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
