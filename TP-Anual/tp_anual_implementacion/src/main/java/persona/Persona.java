package persona;
import contribucion.DistribucionDeVianda;
import contribucion.MotivoDeDistribucion;
import documentacion.Documento;
import heladera.Heladera;
import localizacion.Direccion;
import suscripcion.GestorDeSuscripciones;
import suscripcion.TipoSuscripcion;

import java.time.LocalDate;

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
