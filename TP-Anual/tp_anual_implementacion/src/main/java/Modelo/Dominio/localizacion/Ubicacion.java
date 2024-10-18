package Modelo.Dominio.localizacion;

import jakarta.persistence.*;

@Entity
@Table(name = "Ubicacion")
public class Ubicacion {
    @Id
    @GeneratedValue
    private Integer id_ubicaion;
    @OneToOne
    @JoinColumn(name = "id_punto_en_el_mapa", referencedColumnName = "id_punto_en_el_mapa")
    private PuntoEnElMapa punto;
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    private Direccion direccion;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "nombre_del_punto")
    private String nombreDelPunto;

    public Ubicacion(Direccion direccion, String ciudad, String nombre){
        if(direccion == null ){throw new IllegalArgumentException("La direccion es obligatoria");}
        if(nombre == null ){throw new IllegalArgumentException("El nombre del punto es obligatorio");}
        this.nombreDelPunto = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        // Necesario consultar una API para que dada una direccion se obtengan latitud y longitud y
        // con eso instanciar el punto en el mapa
    }

    // ----------> Getters y Setters
    public void setNombreDelPunto(String nombreDelPunto) { this.nombreDelPunto = nombreDelPunto; }
    public String getNombreDelPunto() { return nombreDelPunto; }
    public Direccion getDireccion() { return direccion; }
    //public String getDireccion() { return direccion.toString(); }
    public PuntoEnElMapa getPunto() { return punto; }
    public String getCiudad() { return ciudad; }
}

