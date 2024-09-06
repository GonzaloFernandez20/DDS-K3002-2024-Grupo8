package Modelo.Dominio.incidentes;

import Modelo.Dominio.colaborador.Colaborador;

public class FallaTecnica extends Incidente{
    private Colaborador colaboradorInformante;
    private Informe informe;

    public FallaTecnica(Colaborador colaboradorInformante, Informe informe,ReporteIncidente reporteIncidente) {
        super(reporteIncidente);
        this.colaboradorInformante = colaboradorInformante;
        this.informe = informe;
    }

    public Colaborador getColaboradorInformante() {
        return colaboradorInformante;
    }

    public void setColaboradorInformante(Colaborador colaboradorInformante) {
        this.colaboradorInformante = colaboradorInformante;
    }

    public Informe getInforme() {
        return informe;
    }

    public void setInforme(Informe informe) {
        this.informe = informe;
    }
}
