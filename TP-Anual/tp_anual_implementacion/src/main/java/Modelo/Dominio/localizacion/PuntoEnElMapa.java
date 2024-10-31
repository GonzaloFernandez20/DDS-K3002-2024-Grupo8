package Modelo.Dominio.localizacion;

import jakarta.persistence.*;

@Entity
@Table(name = "PuntoEnElMapa")
public class PuntoEnElMapa {
    @Id
    @GeneratedValue
    private Integer id_punto_en_el_mapa;
    @Column(name = "latitud")
    double latitud;
    @Column(name = "longitud")
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