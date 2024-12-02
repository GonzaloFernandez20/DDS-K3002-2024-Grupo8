package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.Persona.PersonaHumana;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class    GestorDeAccesosAHeladeras {
    private static GestorDeAccesosAHeladeras instancia;
    private final List<SolicitudTarjeta> tarjetasPendientesDeEntrega;
    private final List<AccesoAHeladeras> tarjetasRegistradas;

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
    public boolean autorizarApertura(String codigoDeTarjeta, Heladera heladera){
        Optional <AccesoAHeladeras> acceso = tarjetasRegistradas.stream()
                                                                .filter(unAcceso -> codigoDeTarjeta.equals(unAcceso.getCodigoTarjeta()))
                                                                .findFirst();
        if (acceso.isPresent()){
            return acceso.get().estaAutorizadaLaApertura(heladera); // Chequea si tiene un permiso hecho
        }else return false; // Si devuelve false es porque la tarjeta no esta registrada en el sistema, no autorizo que abra la heladera
    }

    public void generarSolicitud(PersonaHumana destinatario, int cantidadDeTarjetas){
        SolicitudTarjeta solicitudTarjeta = new SolicitudTarjeta(destinatario, cantidadDeTarjetas);
        tarjetasPendientesDeEntrega.add(solicitudTarjeta); }

    public void eliminarSolicitud(SolicitudTarjeta solicitud){ tarjetasPendientesDeEntrega.remove(solicitud); }

    public void registrarTarjeta(AccesoAHeladeras tarjeta){
        System.out.println(tarjeta.getPersonaHumana());
        System.out.println(tarjeta.getCodigoTarjeta());
        if(tarjetasPendientesDeEntrega.stream().anyMatch(solicitudTarjeta -> solicitudTarjeta.esElMismoDestinatario(tarjeta.getPersonaHumana()) && solicitudTarjeta.esLaMismaTarjeta(tarjeta.getCodigoTarjeta()))){
            tarjetasRegistradas.add(tarjeta);
        }else {
           throw new IllegalArgumentException("La tarjeta no existe");
        }

    }
}

