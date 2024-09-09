package Repositorios;

import java.util.List;

public class IncidentesPeristencia {
    private static IncidentesPeristencia instancia;
    private List<Incidente> incidentes = null;


    public static IncidentesPeristencia getInstancia(){
        if(instancia == null){
            instancia = new IncidentesPeristencia();
        }
        return instancia;
    }

    public List<Incidente> getIncidentes() {return incidentes;}

    public void sumarIncidente(Incidente incidente){incidentes.add(incidente);}
    public List<Incidente> getFallasTecnicas(){
        return incidentes.stream().filter(incidente -> incidente instanceof FallaTecnica).toList();
    }
}
