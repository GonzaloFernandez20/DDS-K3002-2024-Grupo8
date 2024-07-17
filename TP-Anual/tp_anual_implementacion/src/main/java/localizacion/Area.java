package localizacion;

public class Area {
    private Ubicacion ubicacion;
    private Integer radio;

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getRadio() {
        return radio;
    }

    public void setRadio(Integer radio) {
        this.radio = radio;
    }

    public Area(Ubicacion ubicacion, Integer radio) {
        this.ubicacion = ubicacion;
        this.radio = radio;
    }
    public boolean areaContiene(Ubicacion ubicacion){
        //FUNCION QUE DETERMINE EN FUNCION DEL PUNTO EN EL MAPA DE LA UBICACION SI CAE DENTRO DEL AREA
        return true;
    }
    public boolean seSolapaCon(Area otroArea){
        //FUNCION MATEMATICA A IMPLEMENTAR
        return true;
    }
}
