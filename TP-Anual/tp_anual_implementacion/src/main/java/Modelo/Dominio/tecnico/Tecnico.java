package Modelo.Dominio.tecnico;

import Modelo.Dominio.incidentes.EstadoDelIncidente;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.incidentes.Informe;
import Modelo.Dominio.incidentes.VisitaPorIncidente;
import localizacion.Area;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.persona.PersonaHumana;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tecnico {
    private String cuil;
    private MedioDeContacto medioDeContacto;
    private Area areaCobertura;
    private PersonaHumana persona;
    private List<Incidente> incidentesPendientes = new ArrayList<>();

    public Tecnico(PersonaHumana persona, String cuil, Area areaCobertura) {
        this.persona = persona;
        this.cuil = cuil;
        this.areaCobertura = areaCobertura;
        this.medioDeContacto = null;
    }

    public void setNombre(String nombre) {persona.setNombre(nombre);}

    public void setApellido(String apellido) { persona.setApellido(apellido); }

    public void setMedioDeContacto(MedioDeContacto medio) { this.medioDeContacto = medio; }

    public Documento getDocumento() {
        return persona.getDocumento();
    }
    public String getNombre() {
        return persona.getNombre();
    }

    public String getApellido() {
        return persona.getApellido();
    }

    public LocalDate getFechaDeNacimiento() { return persona.getFechaDeNacimiento(); }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public boolean estaCercaDe(Ubicacion ubicacion){
        return areaCobertura.areaContiene(ubicacion);
    }
    public boolean coincideConElArea(Area area){
        return areaCobertura.seSolapaCon(area);
    }
    public void notificarDeIncidente(Incidente incidente){
        incidentesPendientes.add(incidente);
        //notificar via mail tmb o qsy
    }
    public void atenderUnIncidente(Informe informe, EstadoDelIncidente estadoDelIncidente){
        Incidente incidenteAAtender =  incidentesPendientes.get(0);
        VisitaPorIncidente visita = new VisitaPorIncidente(this, LocalDate.now(),incidenteAAtender,informe,estadoDelIncidente);
        incidenteAAtender.atender(visita);
    }

}
