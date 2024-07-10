package localizacion;

public class PuntoEnElMapa {
    double latitud;
    double longitud;

    public PuntoEnElMapa(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}