package colaborador;

import contribucion.Contribucion;
import contribucion.OfertaDeProductos;
import heladera.Direccion;
import medios_de_contacto.MedioDeContacto;

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