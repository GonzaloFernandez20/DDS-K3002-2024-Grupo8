package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.heladera.Heladera;
import org.jetbrains.annotations.NotNull;

public class DistribucionDeViandaDTO {
    Colaborador colaborador;
    Heladera heladeraDeOrigen;
    Heladera heladeraDestino;
    MotivoDeDistribucion motivoDeDistribucion;
    int cantidadDeViandas;


    public DistribucionDeViandaDTO(Colaborador colaborador,
                                Heladera heladeraDeOrigen,
                                Heladera heladeraDestino,
                                MotivoDeDistribucion motivo,
                                int cantidadDeViandas) {
        this.colaborador = colaborador;
        this.heladeraDestino = heladeraDestino;
        this.heladeraDeOrigen = heladeraDeOrigen;
        this.motivoDeDistribucion = motivo;
        this.cantidadDeViandas = cantidadDeViandas;
    }


    public int getCantidadDeViandas() {
        return cantidadDeViandas;
    }

    public void setCantidadDeViandas(int cantidadDeViandas) {
        this.cantidadDeViandas = cantidadDeViandas;
    }

    public MotivoDeDistribucion getMotivoDeDistribucion() {
        return motivoDeDistribucion;
    }

    public void setMotivoDeDistribucion(MotivoDeDistribucion motivoDeDistribucion) {
        this.motivoDeDistribucion = motivoDeDistribucion;
    }

    public Heladera getHeladeraDestino() {
        return heladeraDestino;
    }

    public void setHeladeraDestino(Heladera heladeraDestino) {
        this.heladeraDestino = heladeraDestino;
    }

    public Heladera getHeladeraDeOrigen() {
        return heladeraDeOrigen;
    }

    public void setHeladeraDeOrigen(Heladera heladeraDeOrigen) {
        this.heladeraDeOrigen = heladeraDeOrigen;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
