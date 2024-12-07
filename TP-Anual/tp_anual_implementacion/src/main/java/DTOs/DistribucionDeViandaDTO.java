package DTOs;

public class DistribucionDeViandaDTO {
    private int heladeraDeOrigenID;
    private int heladeraDestinoID;
    private String motivoDeDistribucion;
    private int cantidadDeViandas;

    //Constructor ----------------------------------------------------------------------------------------------------
    public DistribucionDeViandaDTO() {

    }

    //Getters y Setters -----------------------------------------------------------------------------------------------
    public int getCantidadDeViandas() {return cantidadDeViandas;}
    public void setCantidadDeViandas(int cantidadDeViandas) {
        this.cantidadDeViandas = cantidadDeViandas;
    }

    public String getMotivoDeDistribucion() {
        return motivoDeDistribucion;
    }
    public void setMotivoDeDistribucion(String motivoDeDistribucion) {this.motivoDeDistribucion = motivoDeDistribucion;}

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
