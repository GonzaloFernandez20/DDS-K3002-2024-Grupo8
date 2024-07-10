package localizacion;

public class Ubicacion {
    PuntoEnElMapa punto;
    Direccion direccion;
    String ciudad;
    String nombre;

    public Ubicacion(PuntoEnElMapa punto, Direccion direccion, String ciudad, String nombre){
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.punto = punto;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    
    public Direccion getDireccion() { return direccion; }

    public PuntoEnElMapa getPunto() { return punto; }

    public String getCiudad() { return ciudad; }
}

