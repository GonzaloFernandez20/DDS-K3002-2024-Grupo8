package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;

public abstract class ReporteSemanalPorHeladera extends  ReporteSemanal{
    private Heladera heladera;

    public void ReporteSemanalPorHeladera(Heladera heladera) {
        this.heladera = heladera;
    }

    public boolean esUnReporteDeMiHeladera(Heladera heladeraQueNotifico) {
        return heladeraQueNotifico == heladera;
    }

    public abstract void reportar(Integer cantidadViandas);
}
