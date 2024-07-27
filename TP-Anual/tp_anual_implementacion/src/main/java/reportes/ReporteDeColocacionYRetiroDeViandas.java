package reportes;

import heladera.Heladera;

public class ReporteDeColocacionYRetiroDeViandas extends ReporteSemanalPorHeladera{
    private Integer colocacionesYRetiros;

    @Override
    public void reportar(Integer cantidadViandas) {
        colocacionesYRetiros += cantidadViandas;
    }
}
