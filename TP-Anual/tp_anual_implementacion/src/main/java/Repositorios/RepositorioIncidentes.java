package Repositorios;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.Alerta;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioIncidentes {
    private static RepositorioIncidentes instancia;
    private List<Incidente> incidentes;

    public static RepositorioIncidentes getInstancia(){
        if(instancia == null){
            instancia = new RepositorioIncidentes();
        }
        return instancia;
    }

    public List<Incidente> getIncidentes() {
        verificarExistenciaDeIncidentes();
        return incidentes;
    }

    public void sumarIncidente(Incidente incidente){
        verificarExistenciaDeIncidentes();
        incidentes.add(incidente);
        System.out.println("Agregue " + incidente.obtenerInformacion());
    }

    private void verificarExistenciaDeIncidentes(){
        if(incidentes == null) {
            incidentes = new ArrayList<>();
        }
    }

    public void limpiarInstancia() {
        instancia = null;
    }

    public List<FallaTecnica> getFallasTecnicas(){
        verificarExistenciaDeIncidentes();
        return incidentes.stream().filter(incidente -> incidente instanceof FallaTecnica).map(incidente -> (FallaTecnica) incidente).toList();
    }

    public List<Alerta> getAlertas() {
        verificarExistenciaDeIncidentes();
        return incidentes.stream().filter(incidente -> incidente instanceof Alerta).map(incidente -> (Alerta) incidente).toList();
    }

    public List<FallaTecnica> getFallasTecnicasDeHeladeraEntreFechas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
        return getFallasTecnicas().stream().filter( falla -> falla.getHeladeraDondeOcurrio().equals(heladera) && falla.sucedioEntre(fechaInicio, fechaFin)).toList();
    }
}
