package incidentes;

import colaborador.Colaborador;

public class FallaTecnica {
    Colaborador colaboradorInformante;
    Informe informe;

    public FallaTecnica(Colaborador colaboradorInformante, Informe informe) {
        super();
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
