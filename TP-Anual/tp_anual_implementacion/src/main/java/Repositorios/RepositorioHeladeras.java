package Repositorios;


import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.Incidente;

import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras {
    // DEBE USAR DTOs

    private static RepositorioHeladeras instancia;
    private final List<Heladera> heladeras = new ArrayList<>();

    public static RepositorioHeladeras getInstancia(){
        if(instancia == null){
            instancia = new RepositorioHeladeras();
        }
        return instancia;
    }

    public List<Heladera> getHeladeras() {return heladeras;}
}
