package persona;
import documentacion.Documento;
import localizacion.Direccion;
public abstract class Persona {
    public Direccion direccion;
    public Documento documento;

    public Persona(Direccion direccion, Documento documento) {
        this.direccion = direccion;
        this.documento = documento;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Documento getDocumento() { return documento; }

    public void setDocumento(Documento documento) { this.documento = documento; }
}
