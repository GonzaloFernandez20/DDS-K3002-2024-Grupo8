package persona_vulnerable;
import heladera.Heladera;
import heladera.Vianda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccesoAHeladeras {
    PersonaSituacionVulnerable personaSituacionVulnerable;
    String codigoTarjeta;
    int cantUsosRestantesPorDia;
    List <Acceso> historicoDeAccesosAHeladera;
    LocalDate fechaUltimoUso;

    public AccesoAHeladeras(PersonaSituacionVulnerable persona, String codigoTarjeta){
        this.personaSituacionVulnerable = persona;
        this.codigoTarjeta = codigoTarjeta;
        this.reiniciarUsosPermitidos();
        //this.codigoTarjeta = String.valueOf(Sistema.getInstancia().getContadorDeVinculaciones()); // --> Revisar
        this.historicoDeAccesosAHeladera = new ArrayList<>();
    }

    public boolean validarUsoDisponible(){
        consultarUltimoAcceso();
        return cantUsosRestantesPorDia > 0;

        // --> TODO: Manejo de error en caso de que no tenga historicoDeAccesosAHeladera disponibles
        // --> TODO: Si La heladera estuviera vacia, no deberia restar un uso de la tarjeta
    }

    public void registrarAcceso(Vianda vianda, Heladera heladera){
        historicoDeAccesosAHeladera.add(new Acceso(heladera,
                vianda,
                LocalDate.now()));
        heladera.sacarVianda(vianda);
        cantUsosRestantesPorDia --;
    }

    private void reiniciarUsosPermitidos() {
        cantUsosRestantesPorDia = 4 + personaSituacionVulnerable.getCantMenores() * 2;
    }

    private void consultarUltimoAcceso(){
        LocalDate fechaDeHoy = LocalDate.now();

        if (fechaUltimoUso.isBefore(fechaDeHoy)){
            reiniciarUsosPermitidos();
            fechaUltimoUso = fechaDeHoy;
        }
    }

    public int getCantUsosRestantesPorDia() { return cantUsosRestantesPorDia; }
    public String getCodigoTarjeta() { return codigoTarjeta; }
    public PersonaSituacionVulnerable getPersonaSituacionVulnerable() { return personaSituacionVulnerable; }
}