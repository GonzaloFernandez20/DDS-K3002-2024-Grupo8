package heladera;

import colaborador.Colaborador;

public class AvisoIntentoDeRobo {
    Colaborador colaboradorACargo;

    public AvisoIntentoDeRobo(Colaborador colaboradorACargo) {
        this.colaboradorACargo = colaboradorACargo;
    }

    void notificar() {
        colaboradorACargo.serNotificado();
    }
}
