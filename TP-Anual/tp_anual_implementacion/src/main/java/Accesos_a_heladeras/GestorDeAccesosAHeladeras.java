package Accesos_a_heladeras;

import heladera.Heladera;
import persona.PersonaHumana;

import java.util.ArrayList;
import java.util.List;


public class GestorDeAccesosAHeladeras {
    private static GestorDeAccesosAHeladeras instancia;
    private List<SolicitudTarjeta> tarjetasPendientesDeEntrega;
    private List<AccesoAHeladeras> tarjetasRegistradas;

    // ------------------------------------------------
    private GestorDeAccesosAHeladeras() {
        this.tarjetasRegistradas = new ArrayList<>();
        this.tarjetasPendientesDeEntrega = new ArrayList<>();
    }

    public static GestorDeAccesosAHeladeras getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeAccesosAHeladeras();
        }
        return instancia;
    }
    // ------------------------------------------------

    public void autorizarApertura(String codigoDeTarjeta, Heladera heladera){

    }

    public void generarSolicitud(PersonaHumana destinatario, int cantidadDeTarjetas){
        tarjetasPendientesDeEntrega.add(new SolicitudTarjeta(destinatario, cantidadDeTarjetas)); }

    public void eliminarSolicitud(SolicitudTarjeta solicitud){
        tarjetasPendientesDeEntrega.remove(solicitud); }
}

