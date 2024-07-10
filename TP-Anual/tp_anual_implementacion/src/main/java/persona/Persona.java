package persona;
import documentacion.Documento;
import localizacion.Direccion;
public abstract class Persona {
    public  Direccion direccion;

    public Persona() {
        this.direccion = null;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Documento getDocumento() {
        return null;
    }

    public void setDocumento(Documento documento) {

    }
}
