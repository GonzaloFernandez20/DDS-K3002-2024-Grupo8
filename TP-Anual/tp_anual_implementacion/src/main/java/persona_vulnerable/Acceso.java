package persona_vulnerable;

import heladera.Heladera;
import heladera.Vianda;
import java.time.LocalDate;

public class Acceso {
    Heladera heladera;
    Vianda vianda;
    LocalDate fecha;
    public Acceso(Heladera heladera, Vianda vianda, LocalDate fecha){
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
    public LocalDate getFecha() {
        return fecha;
    }
}
