package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;

public class ViandasPorHeladera{
    private Heladera heladera;
    private Integer ViandasRetiradas;
    private Integer ViandasColocadas;

    public ViandasPorHeladera(Heladera heladera, Integer viandasRetiradas, Integer viandasColocadas) {
        this.heladera = heladera;
        ViandasRetiradas = viandasRetiradas;
        ViandasColocadas = viandasColocadas;
    }

    public Heladera getHeladera() { return heladera; }
    public Integer getViandasRetiradas() { return ViandasRetiradas; }
    public Integer getViandasColocadas() { return ViandasColocadas; }
}
