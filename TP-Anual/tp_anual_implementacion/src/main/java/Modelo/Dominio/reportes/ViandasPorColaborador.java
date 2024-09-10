package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;

public class ViandasPorColaborador{
    private Colaborador colaborador;
    private Integer cantidadDeViandas;

    public ViandasPorColaborador(Colaborador colaborador, Integer cantidadDeViandas) {
        this.colaborador = colaborador;
        this.cantidadDeViandas = cantidadDeViandas;
    }
}
