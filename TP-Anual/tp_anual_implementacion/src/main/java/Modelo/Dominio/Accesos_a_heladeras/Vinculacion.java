package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.persona_vulnerable.PersonaSituacionVulnerable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;

public class Vinculacion extends AccesoAHeladeras{
    private PersonaSituacionVulnerable personaSituacionVulnerable;
    private Colaborador colaboradorQueRegistro;
    private LocalDate fechaRegistro;
    private int cantUsosRestantesPorDia;
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
        Apertura nuevoApertura = new Apertura(heladera, RETIRAR_VIANDA, viandaRetirada);
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
}
