package colaborador;

import contribucion.Contribucion;
import contribucion.OfertaDeProductos;
import documentacion.Documento;
import heladera.Heladera;
import localizacion.Direccion;
import medios_de_contacto.MedioDeContacto;
import nuestras_excepciones.ColaboracionInvalida;
import persona.Persona;
import sistema.GestorDeSuscripciones;
import suscripcion.SuscripcionDesperfectoHeladera;
import suscripcion.TipoSuscripcion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colaborador {
    private List<MedioDeContacto> mediosDeContacto;
    private List<Contribucion> contribucionesRealizadas;
    private double puntos;
    private Persona persona;
    private Permiso permiso;
    private Integer tiempoMaximo;
    GestorDeSuscripciones gestorDeSuscripciones;

    public Colaborador(Persona persona){
        this.mediosDeContacto = new ArrayList<>();
        this.contribucionesRealizadas = new ArrayList<>();
        this.puntos = 0;
        this.persona = persona;
    }

    public void setGestorDeSuscripciones(GestorDeSuscripciones gestorDeSuscripciones) {this.gestorDeSuscripciones = gestorDeSuscripciones;}

    public List<Contribucion> getContribucionesRealizadas() { return contribucionesRealizadas; }

    public Boolean tieneMedioDeContacto(MedioDeContacto medioDeContacto) {
        return mediosDeContacto.stream().anyMatch(medio -> medio.getIdentificacion().equals(medioDeContacto.getIdentificacion()));
    }

    public Boolean tieneDocumentoSegunNumeroYTipo(Documento documento) {
        if(this.getPersona().getDocumento() == null){
            return false;
        } else {
            return this.getPersona().getDocumento().esDocumentoSegunNumeroYTipo(documento);
        }
    }

    public void setDocumento(Documento documento) {
        if(documento != null) {
            if(!persona.getDocumento().equals(documento)) {
                persona.setDocumento(documento);
            }
        } else throw new RuntimeException("El documento es null");
    }

    public Documento getDocumento(){
        return persona.getDocumento();
    }

    public void setDireccion(Direccion direccion) { this.persona.setDireccion(direccion); }
    public Direccion getDireccion() { return persona.getDireccion();}

    public double getPuntos() { return puntos; }

    public List<MedioDeContacto> getMediosDeContacto() { return mediosDeContacto; }

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
        if(medioDeContacto != null)
            mediosDeContacto.add(medioDeContacto);
        else throw new RuntimeException("El medio de contacto es null");
    }

    public void sacarMedioDeContacto(MedioDeContacto medioDeContacto) { mediosDeContacto.remove(medioDeContacto); }

    public void sumarContribucion(Contribucion contribucion){
        // el tiempo se escribe en segundos, minutos u horas? tiempoMaximo actualmente esta escrito en horas
        if(contribucion.requieroAcceso()) {
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
        }
    }

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
        return this.persona;
    }

    public void suscribirmeA(Heladera heladera, TipoSuscripcion tipoSuscripcion, Integer n) {
        gestorDeSuscripciones.getInstancia().suscribirA(this, n, tipoSuscripcion, heladera);
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
    }
}