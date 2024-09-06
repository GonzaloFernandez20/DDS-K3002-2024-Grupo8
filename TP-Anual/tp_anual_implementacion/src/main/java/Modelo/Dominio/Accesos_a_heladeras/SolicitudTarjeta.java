package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.persona.PersonaHumana;

public class SolicitudTarjeta {
    private PersonaHumana destinatario;
    private int cantidadDeTarjetas;

    public SolicitudTarjeta(PersonaHumana destinatario, int cantidadDeTarjetas) {
        this.destinatario = destinatario;
        this.cantidadDeTarjetas = cantidadDeTarjetas;
    }
}
