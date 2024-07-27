package sistema;


import incidentes.EstadoDelIncidente;
import incidentes.Incidente;
import tecnico.Tecnico;

import java.util.List;

public final class GestorDeIncidentes {
    public List<Incidente> incidentesNoSolucionados;
    public List<Incidente> incidentesSolucionados;
    private static GestorDeIncidentes instancia = null;

    public void recibirReporte(Incidente incidente){
        incidentesNoSolucionados.add(incidente);
        this.avisarAlTecnicoPorIncidente(incidente);
    }
    public void avisarAlTecnicoPorIncidente(Incidente incidente){
        Sistema sistema = Sistema.getInstancia();
        Tecnico tecnicoMasCercano = sistema.darTecnicoMasCercano(incidente.getHeladera().getUbicacion());
        tecnicoMasCercano.notificarDeIncidente(incidente);
    }

    public void pasarIncidenteASolucionado(Incidente incidenteRecibido){
        //No sé por qué tira un error cuando hgo esto
        //Incidente incidenteAux = incidentesNoSolucionados.stream().findFirst(unIncidente -> unIncidente.equals(incidenteRecibido));
        //Estamos asumiendo que esto no debería fallar porque es un funcionamiento interno del sistema
        //Además hacer una excepción para esto va a ser un re lio
        Incidente incidenteHallado = null;
        if(incidenteHallado.getEstadoDelIncidente().equals(EstadoDelIncidente.SOLUCIONADO)){//Chequeo por las dudas
            for (int i = 0; i < incidentesNoSolucionados.size(); i++) {
                if (incidenteRecibido.equals(incidentesNoSolucionados.get(i))) {
                    incidenteHallado = incidentesNoSolucionados.get(i);
                    i = incidentesNoSolucionados.size();
                }
            }
            incidentesNoSolucionados.remove(incidenteHallado);
            incidentesSolucionados.add(incidenteHallado);
        }
    }

    public static GestorDeIncidentes getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeIncidentes();
        }
        return instancia;
    }

}
