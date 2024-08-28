package Accesos_a_heladeras;

import heladera.Heladera;

import java.util.List;

public abstract class AccesoAHeladeras {
    protected String codigoTarjeta;
    protected List <Acceso> historicoDeAccesosHeladera;

    public AccesoAHeladeras(List<Acceso> historicoDeAccesosHeladera, String codigoTarjeta) {
        this.historicoDeAccesosHeladera = historicoDeAccesosHeladera;
        this.codigoTarjeta = codigoTarjeta;
    }

    public abstract Boolean aperturaAutorizada(Heladera heladera);
}