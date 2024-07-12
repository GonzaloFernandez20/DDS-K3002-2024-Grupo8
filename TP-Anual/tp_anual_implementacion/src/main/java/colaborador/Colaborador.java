package colaborador;

import contribucion.Contribucion;
import contribucion.OfertaDeProductos;
import documentacion.Documento;
import localizacion.Direccion;
import medios_de_contacto.MedioDeContacto;
import persona.Persona;
import persona.PersonaHumana;
import persona.PersonaJuridica;
import java.util.ArrayList;
import java.util.List;

public class Colaborador {
    private List<MedioDeContacto> mediosDeContacto;
    private List<Contribucion> contribucionesRealizadas;
    private double puntos;
    private Persona persona;

    public Colaborador(Persona persona){
        this.mediosDeContacto = new ArrayList<>();
        this.contribucionesRealizadas = new ArrayList<>();
        this.puntos = 0;
        this.persona = persona;
    }
    public List<Contribucion> getContribucionesRealizadas() { return contribucionesRealizadas; }

    public Boolean tieneMail(MedioDeContacto mail) {
        return mediosDeContacto.stream().anyMatch(medio -> medio.equals(mail));
    }

    public Boolean tieneDocumentoSegunNumeroYTipo(Documento documento) {
        return this.getPersona().getDocumento().esDocumentoSegunNumeroYTipo(documento);
    }
    public Documento getDocumento(){
        return persona.getDocumento();
    }

    public void setDireccion(Direccion direccion) { this.persona.setDireccion(direccion); }
    public Direccion getDireccion() { return persona.getDireccion();}

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

    public void serNotificado() {
        // no hay suficiente informacion sobre como se comporta PersonaJuridica al
        // recibir la notificacion
    }

    public Persona getPersona() {
        return persona;
    }
}