package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.persona.Persona;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;
@Entity
@Table(name = "Vinculacion")
public class Vinculacion extends AccesoAHeladeras{
    @OneToOne
    @JoinColumn(name = "persona_en_situacion_vulnerabre", referencedColumnName = "id_persona_en_situacion_vulnerable")
    private PersonaSituacionVulnerable personaSituacionVulnerable;
    @OneToOne
    @JoinColumn(name = "colaborador_registrante", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorQueRegistro;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @Column(name = "usos_restantes_por_dia")
    private int cantUsosRestantesPorDia;
    @Column(name = "fecha_ultimo_uso")
    private LocalDate fechaUltimoUso;

    public Vinculacion(String codigoTarjeta,
                       PersonaSituacionVulnerable personaSituacionVulnerable,
                       Colaborador colaboradorQueRegistro) {
        this.codigoTarjeta = codigoTarjeta;
        this.personaSituacionVulnerable = personaSituacionVulnerable;
        this.colaboradorQueRegistro = colaboradorQueRegistro;
        reiniciarUsosPermitidos();
        this.fechaRegistro = LocalDate.now();
        this.fechaUltimoUso = LocalDate.now();
        this.historicoDeAccesosHeladera = new ArrayList<>();
        GestorDeAccesosAHeladeras.getInstancia().registrarTarjeta(this);
    }

    @Override
    public boolean aperturaAutorizada(Heladera heladera) {
        consultarUltimoAcceso();
        if(cantUsosRestantesPorDia > 0) {
            registrarAcceso(heladera);
            return true;
        } else return false;
    }

    private void registrarAcceso(Heladera heladera) {
        List <Vianda> viandaRetirada = heladera.retirarViandas(1);
        viandaRetirada.getFirst().setEstadoVianda(EstadoVianda.RETIRADA);
        PermisoDeAperturaParaRetirar nuevoApertura = new PermisoDeAperturaParaRetirar(heladera, RETIRAR_VIANDA);
        historicoDeAccesosHeladera.add(nuevoApertura);
        cantUsosRestantesPorDia--;
    }


    private void consultarUltimoAcceso() {
        LocalDate fechaDeHoy = LocalDate.now();
        if (fechaUltimoUso.isBefore(fechaDeHoy)){
            reiniciarUsosPermitidos();
            fechaUltimoUso = fechaDeHoy;
        }
    }

    private void reiniciarUsosPermitidos(){
        cantUsosRestantesPorDia = 4 + personaSituacionVulnerable.getCantMenores() * 2;
    }

    // ----------> Getters y Setters

    public LocalDate getFechaUltimoUso() {
        return fechaUltimoUso;
    }

    public void setFechaUltimoUso(LocalDate fechaUltimoUso) {
        this.fechaUltimoUso = fechaUltimoUso;
    }

    public int getCantUsosRestantesPorDia() {
        return cantUsosRestantesPorDia;
    }

    public void setCantUsosRestantesPorDia(int cantUsosRestantesPorDia) {
        this.cantUsosRestantesPorDia = cantUsosRestantesPorDia;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Persona getPersonaHumana() {
        return (PersonaHumana) colaboradorQueRegistro.getPersona();
    }

    public void setColaboradorQueRegistro(Colaborador colaboradorQueRegistro) {
        this.colaboradorQueRegistro = colaboradorQueRegistro;
    }

    public PersonaSituacionVulnerable getPersonaSituacionVulnerable() {
        return personaSituacionVulnerable;
    }

    public void setPersonaSituacionVulnerable(PersonaSituacionVulnerable personaSituacionVulnerable) {
        this.personaSituacionVulnerable = personaSituacionVulnerable;
    }
}
