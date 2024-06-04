package tp_anual.tp_anual_implementacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Colaborador {
    private List<MedioDeContacto> mediosDeContacto;
    private Direccion direccion;
    private List<Contribucion> contribucionesRealizadas;
    private double puntos;

    public Colaborador(Direccion direccion) {
        this.direccion = direccion;
        this.mediosDeContacto = new ArrayList<>();
        this.contribucionesRealizadas = new ArrayList<>();
        this.puntos = 0;
    }
    
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public Direccion getDireccion() { return direccion; }

    public double getPuntos() { return puntos; }

    public List<MedioDeContacto> getMediosDeContacto() { return mediosDeContacto; }

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) { mediosDeContacto.add(medioDeContacto); }

    public void sacarMedioDeContacto(MedioDeContacto medioDeContacto) { mediosDeContacto.remove(medioDeContacto); }

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
}
    
class PersonaHumana extends Colaborador{
    String nombre;
    String apellido;
    Date fechaDeNacimiento;

    public PersonaHumana(String nombre, String apellido, Date fechaDeNacimiento, Direccion direccion) {
        super(direccion);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}

class PersonaJuridica extends Colaborador{
    String razonSocial;
    TipoOrganizacion tipoDeOrganizacion;
    String rubro;

    public PersonaJuridica(String razonSocial, TipoOrganizacion tipoDeOrganizacion, String rubro, Direccion direccion) {
        super(direccion);
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
    }

    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public void setTipoDeOrganizacion(TipoOrganizacion tipoDeOrganizacion) { this.tipoDeOrganizacion = tipoDeOrganizacion; }

    public void setRubro(String rubro) { this.rubro = rubro; }

    void serNotificado() {
        // no hay suficiente informacion sobre como se comporta PersonaJuridica al
        // recibir la notificacion
    }
}

enum TipoOrganizacion {
    gubernamental,
    ong,
    empresa,
    institucion
}
