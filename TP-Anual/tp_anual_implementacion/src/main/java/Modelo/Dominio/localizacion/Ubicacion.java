package Modelo.Dominio.localizacion;

public class Ubicacion {
    private PuntoEnElMapa punto;
    private Direccion direccion;
    private String ciudad;
    private String nombreDelPunto;

    public Ubicacion(PuntoEnElMapa punto, Direccion direccion, String ciudad, String nombre){
        this.nombreDelPunto = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.punto = punto;
    }

    // ----------> Getters y Setters
    public void setNombreDelPunto(String nombreDelPunto) { this.nombreDelPunto = nombreDelPunto; }
    public String getNombreDelPunto() { return nombreDelPunto; }
    public Direccion getDireccion() { return direccion; }
    public PuntoEnElMapa getPunto() { return punto; }
    public String getCiudad() { return ciudad; }
}

