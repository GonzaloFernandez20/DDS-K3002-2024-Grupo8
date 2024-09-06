package Modelo.Dominio.colaborador;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.OfertaDeProductos;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import org.jetbrains.annotations.NotNull;
import Modelo.Dominio.persona.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Colaborador {
    private final Persona persona;
    private final List<MedioDeContacto> mediosDeContacto;
    private final List<String> mensajesRecibidos;
    private final List<Contribucion> historialDeContribuciones;
    private AccesoDeColaborador tarjeta;
    private double puntos;

    public Integer cantidadDeDonacionesDeViandaEntre(LocalDate fechaInicio, LocalDate fechaFin){
        if(tarjeta == null){return 0;}
        else{return tarjeta.cantidadDeAperturasPorDonacionesEntre(fechaInicio, fechaFin);}
    }
    // ------------------------------------------------
    public Colaborador(Persona persona,
                       List<MedioDeContacto> mediosDeContacto) {
        this.persona = persona;
        this.mediosDeContacto = mediosDeContacto;
        this.mensajesRecibidos = new ArrayList<>();
        this.historialDeContribuciones = new ArrayList<>();
        this.tarjeta = null;
        this.puntos = 0;
    }
    // ------------------------------------------------

    public void registrarContribucion(Contribucion contribucion){
        historialDeContribuciones.add(contribucion);
        //puntos += contribucion.puntosQueSumaColaborador();
    }

    public void notificar(String mensaje) {
        mensajesRecibidos.add(mensaje);

        for (MedioDeContacto medio : mediosDeContacto){
            medio.notificar(mensaje);
        }
    }

    private void intercambiarPuntos(OfertaDeProductos ofertaSeleccionada) {
        if(ofertaSeleccionada.getPuntosNecesarios() < puntos) {
            puntos -= ofertaSeleccionada.getPuntosNecesarios();
            ofertaSeleccionada.disminuirStock();
        } else {
            // Tirar una Excepcion
        }
    }

    public Boolean tieneMedioDeContacto(MedioDeContacto medioDeContacto) {
        return mediosDeContacto.stream().anyMatch(medio -> medio.equals(medioDeContacto));
    }

    public Boolean tieneDocumentoSegunNumeroYTipo(Documento documento) {
        if(this.persona.getDocumento() == null){
            return false;
        } else {
            return this.persona.getDocumento().esDocumentoSegunNumeroYTipo(documento);
        }
    }

    public Direccion obtenerDireccion() { return persona.getDireccion(); }
    public void sacarMedioDeContacto(@NotNull MedioDeContacto medioDeContacto) { mediosDeContacto.remove(medioDeContacto); }
    public void agregarMedioDeContacto(@NotNull MedioDeContacto nuevoMedio) { mediosDeContacto.add(nuevoMedio);}

    // ---- Getters y Setters

    public Direccion getDireccion() { return persona.getDireccion(); }
    public double getPuntos() { return puntos; }
    public List<MedioDeContacto> getMediosDeContacto() { return mediosDeContacto; }
    public Persona getPersona() { return persona; }
    public AccesoDeColaborador getTarjeta() { return tarjeta; }

}

/*
    public void agregarContribucionEnLista(Contribucion contribucion){
        contribucionesRealizadas.add(contribucion);
            this.realizarContribucion(contribucion);
    }

    private void realizarContribucion(Contribucion contribucion) {
        contribucionesRealizadas.add(contribucion);
        try {
            contribucion.procesarContribucion();
        } catch (ColaboracionInvalida e) {
            throw new RuntimeException(e);
        }
        puntos += contribucion.puntosQueSumaColaborador();
    }

    // ver como se le pregunta al colab si quiere acudir
    public Boolean deseoAcudir() {return true;}

    public Integer quieroMoverCantViandas() {
        Random random = new Random();

        // Generamos un número aleatorio entre 1 y 5
        int randomNumber = random.nextInt(5) + 1;
        return randomNumber;
    }

    public Heladera heladeraAElegir() {
        // no sabemos con que criterios la elige
        return null;
    }

    public Heladera aceptarUnaHeladeraDe(List<Heladera> heladeras) {
        // con que criterio elige??
        return null;
    }
*/
