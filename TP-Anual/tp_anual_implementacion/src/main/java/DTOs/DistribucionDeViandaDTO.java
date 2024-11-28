package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.heladera.Heladera;
import org.jetbrains.annotations.NotNull;

public class DistribucionDeViandaDTO {
    int heladeraDeOrigenID;
    int heladeraDestinoID;
    MotivoDeDistribucion motivoDeDistribucion;
    int cantidadDeViandas;

    //Constructor ----------------------------------------------------------------------------------------------------
    public DistribucionDeViandaDTO() {

    }

    //Getters y Setters -----------------------------------------------------------------------------------------------
    public int getCantidadDeViandas() {return cantidadDeViandas;}
    public void setCantidadDeViandas(int cantidadDeViandas) {
        this.cantidadDeViandas = cantidadDeViandas;
    }

    public MotivoDeDistribucion getMotivoDeDistribucion() {
        return motivoDeDistribucion;
    }
    public void setMotivoDeDistribucion(MotivoDeDistribucion motivoDeDistribucion) {this.motivoDeDistribucion = motivoDeDistribucion;}

    public int getHeladeraDestinoID() {
        return heladeraDestinoID;
    }
    public void setHeladeraDestino(int heladeraDestino) {
        this.heladeraDestinoID = heladeraDestino;
    }

    public int getHeladeraDeOrigenID() {
        return heladeraDeOrigenID;
    }
    public void setHeladeraDeOrigen(int heladeraDeOrigen) {
        this.heladeraDeOrigenID = heladeraDeOrigen;
    }

}
