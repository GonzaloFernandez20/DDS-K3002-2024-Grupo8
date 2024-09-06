package Modelo.Dominio.incidentes;

import Modelo.Dominio.tecnico.Tecnico;

import java.time.LocalDate;

public class VisitaPorIncidente {
    private Tecnico tecnico;
    private LocalDate fecha;
    private Incidente incidente;
    private Informe informe;
    private EstadoDelIncidente estadoDeLaVisita;//posiblemente innecesario

    public boolean visitaSolucionaElIncidente(){
        return this.getEstadoDeLaVisita() == EstadoDelIncidente.SOLUCIONADO;
    }

//MÉTODOS QUE CARECEN DE LÓGICA
    public VisitaPorIncidente(Tecnico tecnico, LocalDate fecha, Incidente incidente, Informe informe, EstadoDelIncidente estadoDelIncidente) {
        this.tecnico = tecnico;
        this.fecha = fecha;
        this.incidente = incidente;
        this.informe = informe;
        this.estadoDeLaVisita = estadoDelIncidente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }

    public Informe getInforme() {
        return informe;
    }

    public void setInforme(Informe informe) {
        this.informe = informe;
    }

    public EstadoDelIncidente getEstadoDeLaVisita() {
        return estadoDeLaVisita;
    }

    public void setEstadoDeLaVisita(EstadoDelIncidente estadoDelIncidente) {
        this.estadoDeLaVisita = estadoDelIncidente;
    }

}
