package Modelo.Dominio.localizacion;

public class Ubicacion {
    private PuntoEnElMapa punto;
    private Direccion direccion;
    private String ciudad;
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
    public PuntoEnElMapa getPunto() { return punto; }
    public String getCiudad() { return ciudad; }
}

