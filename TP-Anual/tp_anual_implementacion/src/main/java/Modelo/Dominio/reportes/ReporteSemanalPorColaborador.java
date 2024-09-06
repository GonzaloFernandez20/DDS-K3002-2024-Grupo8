package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;

public class ReporteSemanalPorColaborador {
    private Colaborador colaborador;
    private Integer cantidadViandas;

    public void reportar(int cantidadViandas) {
        this.cantidadViandas += cantidadViandas;
    }

    public boolean esUnReporteDeMiColaborador(Colaborador colaboradorQueDono) {
        return colaborador == colaboradorQueDono;
    }
}
