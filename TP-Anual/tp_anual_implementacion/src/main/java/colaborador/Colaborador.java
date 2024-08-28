package colaborador;

import Accesos_a_heladeras.AccesoDeColaborador;
import contribucion.Contribucion;
import contribucion.OfertaDeProductos;
import documentacion.Documento;
import heladera.Heladera;
import localizacion.Direccion;
import medios_de_contacto.MedioDeContacto;
import nuestras_excepciones.ColaboracionInvalida;
import org.jetbrains.annotations.NotNull;
import persona.Persona;
import persona.PersonaHumana;
import suscripcion.SuscripcionDesperfectoHeladera;
import suscripcion.TipoSuscripcion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colaborador {
    private final PersonaHumana persona;
    private List<MedioDeContacto> mediosDeContacto;
    private List<String> mensajesRecibidos;
    private List<Contribucion> historialDeContribuciones;
    private AccesoDeColaborador tarjeta;
    private double puntos;

    // ------------------------------------------------

    public Colaborador(PersonaHumana persona,
                       List<MedioDeContacto> mediosDeContacto) {
        this.persona = persona;
        this.mediosDeContacto = mediosDeContacto;
        this.mensajesRecibidos = new ArrayList<>();
        this.historialDeContribuciones = new ArrayList<>();
        this.puntos = 0;
    }
    // ------------------------------------------------

    public void registrarContribucion(Contribucion contribucion){
/*        if(contribucion.requieroAcceso()) {
            RegistroAperturaHeladera registro = new RegistroAperturaHeladera(contribucion, LocalDate.now(), 3, (Heladera) contribucion.getHeladera());
            if(permiso != null) {
                permiso.registrar(registro);
                permiso.sumarRegistro(registro);
                this.realizarContribucion(contribucion);
                agregarContribucionEnLista(contribucion);
                puntos += contribucion.puntosQueSumaColaborador();
            } else {
                // error "El usuario carece de permisos para realizar dicha operacion
                // solicitar permiso y/o direccion si no la dio antes
                permiso.sumarSolicitud(registro);
            }
        }else {
            agregarContribucionEnLista(contribucion);
            puntos += contribucion.puntosQueSumaColaborador();
        }*/
    }

    public void notificar() {
        // no hay suficiente informacion sobre como se comporta PersonaJuridica al
        // recibir la notificacion
    }

/*

*/

    private void intercambiarPuntos(OfertaDeProductos ofertaSeleccionada) {
        if(ofertaSeleccionada.getPuntosNecesarios() < puntos) {
            puntos -= ofertaSeleccionada.getPuntosNecesarios();
            ofertaSeleccionada.disminuirStock();
        } else {
            // Tirar una Excepcion
        }
    }



    public Boolean tieneMedioDeContacto(MedioDeContacto medioDeContacto) {
        return mediosDeContacto.stream().anyMatch(medio -> medio.getIdentificacion().equals(medioDeContacto.getIdentificacion()));
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
    public PersonaHumana getPersona() { return persona; }
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

    public void serNotificadoPorSuscripcion(Heladera heladeraQueNotifico, Integer n, TipoSuscripcion tipoSuscripcion) {
        Integer viandasADistribuir;
        if(this.deseoAcudir()) {
            if(tipoSuscripcion.getClass().equals(SuscripcionDesperfectoHeladera.class)) {
                viandasADistribuir = (heladeraQueNotifico.getViandasEnStock()).size();
            } else {
                viandasADistribuir = this.quieroMoverCantViandas();
            }
            Contribucion contribucion = tipoSuscripcion.crearContribucionDe(this, heladeraQueNotifico, viandasADistribuir);
            this.sumarContribucion(contribucion);
        }
    }*/
