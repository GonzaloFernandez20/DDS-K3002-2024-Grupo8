package Repositorios;

import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.Incidente;

import java.util.List;

public class RepositorioIncidentes {
    private static RepositorioIncidentes instancia;
    private List<Incidente> incidentes = null;


    public static RepositorioIncidentes getInstancia(){
        if(instancia == null){
            instancia = new RepositorioIncidentes();
        }
        return instancia;
    }

    public List<Incidente> getIncidentes() {return incidentes;}

    public void sumarIncidente(Incidente incidente){incidentes.add(incidente);}
    public List<Incidente> getFallasTecnicas(){
        return incidentes.stream().filter(incidente -> incidente instanceof FallaTecnica).toList();
    }
}
