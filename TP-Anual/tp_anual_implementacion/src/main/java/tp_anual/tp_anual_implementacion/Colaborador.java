package tp_anual.tp_anual_implementacion;

import java.util.Date;
import java.util.List;

public class Colaborador {
    private List<MedioDeContacto> mediosDeContacto;
    private Direccion direccion;
    private List<Contribucion> contribucionesRealizadas;
    private double puntos;
    
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public Direccion getDireccion() { return direccion; }

    public double getPuntos() { return puntos; }

    public List<MedioDeContacto> getMediosDeContacto() { return mediosDeContacto; }

    public void sumarContribucion(Contribucion contribucion){
        contribucionesRealizadas.add(contribucion);
        puntos += contribucion.puntosQueSumaColaborador();
    }

    private void intercambiarPuntos(OfertaDeProductos ofertaSeleccionada) {
        if(ofertaSeleccionada.getPuntosNecesarios() < puntos) {
            puntos -= ofertaSeleccionada.getPuntosNecesarios();
            ofertaSeleccionada.disminuirStock();
        } else {
            // Tirar una Excepcion
        }
    }

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) { mediosDeContacto.add(medioDeContacto); }

    public void sacarMedioDeContacto(MedioDeContacto medioDeContacto) { mediosDeContacto.remove(medioDeContacto); }
}
    
class PersonaHumana extends Colaborador{
    String nombre;
    String apellido;
    Date fechaDeNacimiento;
}

class PersonaJuridica extends Colaborador{
    String razonSocial;
    String tipoDeOrganizacion;
    String rubro;

    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public void setTipoDeOrganizacion(String tipoDeOrganizacion) { this.tipoDeOrganizacion = tipoDeOrganizacion; }

    public void setRubro(String rubro) { this.rubro = rubro; }

    void serNotificado() {
        // no hay suficiente informacion sobre como se comporta PersonaJuridica al
        // recibir la notificacion
    }
}

enum Tipo {
    gubernamental,
    ong,
    empresa,
    institucion
}
