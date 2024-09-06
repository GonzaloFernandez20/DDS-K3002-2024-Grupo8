package Modelo.Dominio.contribucion;

public class Imagen {
    private String nombre;
    private String resolucion;
    private String epigrafe;

    public Imagen(String nombre, String resolucion, String epigrafe) {
        this.nombre = nombre;
        this.resolucion = resolucion;
        this.epigrafe = epigrafe;
    }

    public String getNombre() {
        return nombre;
    }

    public String getResolucion() {
        return resolucion;
    }

    public String getEpigrafe() {
        return epigrafe;
    }
}
