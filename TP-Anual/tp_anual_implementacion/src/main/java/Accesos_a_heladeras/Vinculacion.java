package Accesos_a_heladeras;

import colaborador.Colaborador;
import heladera.Heladera;
import persona_vulnerable.PersonaSituacionVulnerable;

import java.time.LocalDate;
import java.util.List;
import static Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;

public class Vinculacion extends AccesoAHeladeras{
    private PersonaSituacionVulnerable personaSituacionVulnerable;
    private Colaborador colaboradorQueRegistro;
    private LocalDate fechaRegistro;
    private int cantUsosRestantesPorDia;
    private LocalDate fechaUltimoUso;

    public Vinculacion(List<Apertura> historicoDeAccesosHeladera,
                       String codigoTarjeta,
                       PersonaSituacionVulnerable personaSituacionVulnerable,
                       Colaborador colaboradorQueRegistro,
                       int cantUsosRestantesPorDia,
                       LocalDate fechaRegistro,
                       LocalDate fechaUltimoUso) {
        super(historicoDeAccesosHeladera, codigoTarjeta);
        this.personaSituacionVulnerable = personaSituacionVulnerable;
        this.colaboradorQueRegistro = colaboradorQueRegistro;
        this.cantUsosRestantesPorDia = cantUsosRestantesPorDia;
        this.fechaRegistro = fechaRegistro;
        this.fechaUltimoUso = fechaUltimoUso;
    }

    public void registrarAcceso(Heladera heladera) {
        Apertura nuevoApertura = new Apertura(heladera, RETIRAR_VIANDA);
        historicoDeAccesosHeladera.add(nuevoApertura);
        cantUsosRestantesPorDia --;
    }

    @Override
    public Boolean aperturaAutorizada(Heladera heladera) {   // Chequear si tiene usos disponibles ...
        consultarUltimoAcceso();
        return cantUsosRestantesPorDia > 0;

        // --> TODO: Manejo de error en caso de que no tenga historicoDeAccesosAHeladera disponibles
        // --> TODO: Si La heladera estuviera vacia, no deberia restar un uso de la tarjeta
    }

    private void reiniciarUsosPermitidos(){
        cantUsosRestantesPorDia = 4 + personaSituacionVulnerable.getCantMenores() * 2;
    }

    private void consultarUltimoAcceso(){
        LocalDate fechaDeHoy = LocalDate.now();

        if (fechaUltimoUso.isBefore(fechaDeHoy)){
            reiniciarUsosPermitidos();
            fechaUltimoUso = fechaDeHoy;
        }
    }
    // ----------> Getters y Setters

    public PersonaSituacionVulnerable getPersonaSituacionVulnerable() {
        return personaSituacionVulnerable;
    }
    public void setPersonaSituacionVulnerable(PersonaSituacionVulnerable personaSituacionVulnerable) {
        this.personaSituacionVulnerable = personaSituacionVulnerable;
    }
    public Colaborador getColaboradorQueRegistro() {
        return colaboradorQueRegistro;
    }
    public void setColaboradorQueRegistro(Colaborador colaboradorQueRegistro) {
        this.colaboradorQueRegistro = colaboradorQueRegistro;
    }
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public int getCantUsosRestantesPorDia() {
        return cantUsosRestantesPorDia;
    }
    public void setCantUsosRestantesPorDia(int cantUsosRestantesPorDia) {
        this.cantUsosRestantesPorDia = cantUsosRestantesPorDia;
    }
    public LocalDate getFechaUltimoUso() {
        return fechaUltimoUso;
    }
    public void setFechaUltimoUso(LocalDate fechaUltimoUso) {
        this.fechaUltimoUso = fechaUltimoUso;
    }
}
