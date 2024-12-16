package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;


public class ViandasPorColaborador{
    private Colaborador colaborador;
    private Integer cantidadDeViandas;

    public ViandasPorColaborador(Colaborador colaborador, Integer cantidadDeViandas) {
        this.colaborador = colaborador;
        this.cantidadDeViandas = cantidadDeViandas;
    }

    public ViandasPorColaborador() {

    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void setCantidadDeViandas(Integer cantidadDeViandas) {
        this.cantidadDeViandas = cantidadDeViandas;
    }

    public Colaborador getColaborador() { return colaborador; }
    public Integer getCantidadDeViandas() { return cantidadDeViandas; }
}
