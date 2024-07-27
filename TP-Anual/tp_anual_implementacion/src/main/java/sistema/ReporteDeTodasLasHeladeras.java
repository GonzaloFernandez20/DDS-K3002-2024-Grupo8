package sistema;

import colaborador.Colaborador;
import heladera.Heladera;
import reportes.ReporteSemanal;
import reportes.ReporteSemanalPorColaborador;
import reportes.ReporteSemanalPorHeladera;
import reportes.ReportesDeViandasPorColaborador;

import java.util.List;

public class ReporteDeTodasLasHeladeras {
    private static ReporteDeTodasLasHeladeras instancia = null;
    private List<ReporteSemanalPorHeladera> reportesSemanalesPorHeladera;
    private List<ReporteSemanalPorColaborador> reportesSemanalesPorColaborador;

    public static ReporteDeTodasLasHeladeras getInstancia() {
        if (instancia == null) {
            instancia = new ReporteDeTodasLasHeladeras();
        }
        return instancia;
    }

    public void recibirReporteHeladeras(Heladera heladera, int cantidadViandas) {
        for(ReporteSemanalPorHeladera reporteSemanalPorHeladera : reportesSemanalesPorHeladera) {
            if (reporteSemanalPorHeladera.esUnReporteDeMiHeladera(heladera)) {
                reporteSemanalPorHeladera.reportar(cantidadViandas);
            }
        }
    }

    public void recibirReporteColaborador(Colaborador colaborador, int cantidadViandas) {
        for(ReporteSemanalPorColaborador ReporteSemanalPorColaborador : reportesSemanalesPorColaborador) {
            if (ReporteSemanalPorColaborador.esUnReporteDeMiColaborador(colaborador)) {
                ReporteSemanalPorColaborador.reportar(cantidadViandas);
            }
        }
    }
}
