package Modelo.Dominio.heladera;

import Modelo.Dominio.colaborador.Colaborador;

public class AvisoIntentoDeRobo {
    Colaborador colaboradorACargo;

    public AvisoIntentoDeRobo(Colaborador colaboradorACargo) {
        this.colaboradorACargo = colaboradorACargo;
    }

    void notificar() {
        colaboradorACargo.serNotificado();
    }
}
