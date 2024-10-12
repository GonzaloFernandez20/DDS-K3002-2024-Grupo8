package Repositorios;

import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;

import java.time.LocalDate;
import java.util.List;

public class RepositorioSuscripciones {

    private static RepositorioSuscripciones instancia;

    private static List<NotificadorDeSuscriptos> suscripciones;

    public static RepositorioSuscripciones getInstancia(){
        if(instancia == null){
            instancia = new RepositorioSuscripciones();
        }
        return instancia;
    }

    public List<NotificadorDeSuscriptos> getSuscripciones() { return suscripciones; }
}