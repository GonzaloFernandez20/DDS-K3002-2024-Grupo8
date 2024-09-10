package Modelo.Dominio.incidentes;

import DTOs.VisitaTecnicaDTO;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Factorys.FactoryVisitaTecnica;

public class GestorDeVisitas {
    public static void registrarVisita(VisitaTecnicaDTO visitaTecnicaDTO){
        VisitaTecnica nuevaVisita = FactoryVisitaTecnica.crearVisitaTecnicaAPartirDe(visitaTecnicaDTO);
        nuevaVisita.getIncidenteAtendido().registrarVisita(nuevaVisita);
        if (nuevaVisita.getEstadoVisita().equals(EstadoDelIncidente.SOLUCIONADO)){
            nuevaVisita.getIncidenteAtendido().getHeladeraDondeOcurrio().setEstado(EstadoHeladera.ACTIVA);
        }
    }
}
