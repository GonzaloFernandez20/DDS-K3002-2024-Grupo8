package Modelo.Dominio.incidentes;

public class Informe {
    private String informe;
    private String imagen;

    public Informe(String informe, String imagen) {
        this.informe = informe;
        this.imagen = imagen;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
