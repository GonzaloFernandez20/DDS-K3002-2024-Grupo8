package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;

import java.util.List;

public abstract class AccesoAHeladeras {
    protected String codigoTarjeta;
    protected List <Apertura> historicoDeAccesosHeladera;

    public abstract boolean aperturaAutorizada(Heladera heladera);
    public String getCodigoTarjeta() { return codigoTarjeta; }
}