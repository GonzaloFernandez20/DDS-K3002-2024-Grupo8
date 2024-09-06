package Modelo.Dominio.reportes;

public class ReporteDeFallas extends ReporteSemanalPorHeladera{
    private Integer cantidadFallas;

    @Override
    public void reportar(Integer cantidadViandas) {
        cantidadFallas++;
    }
}
