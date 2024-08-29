package Accesos_a_heladeras;

import heladera.Heladera;

import java.util.ArrayList;
import java.util.List;

public abstract class AccesoAHeladeras {
    protected String codigoTarjeta;
    protected List <Apertura> historicoDeAccesosHeladera;

    public abstract Boolean aperturaAutorizada(Heladera heladera);
    public String getCodigoTarjeta() { return codigoTarjeta; }
}