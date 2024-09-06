package Modelo.Dominio.localizacion;

public class PuntoEnElMapa {
    double latitud;
    double longitud;

    public PuntoEnElMapa(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // ----------> Getters y Setters
    public double getLatitud() {
        return latitud;
    }
    public double getLongitud() {
        return longitud;
    }
}