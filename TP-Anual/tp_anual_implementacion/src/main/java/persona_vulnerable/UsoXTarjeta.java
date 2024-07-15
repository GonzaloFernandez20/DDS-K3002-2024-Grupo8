package persona_vulnerable;

import heladera.Heladera;
import heladera.Vianda;

import java.time.LocalDateTime;

public class UsoXTarjeta {
    Heladera heladera;
    Vianda vianda;
    LocalDateTime fecha;
    public UsoXTarjeta(Heladera heladera,Vianda vianda,LocalDateTime fecha){
        this.heladera = heladera;
        this.fecha = fecha;
        this.vianda = vianda;
    }
    public Heladera getHeladera() {
        return heladera;
    }
    public Vianda getVianda() {
        return vianda;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
}
