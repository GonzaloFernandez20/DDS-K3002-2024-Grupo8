package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.Persona_vulnerable.PersonaSituacionVulnerable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;

@Slf4j
@Setter
@Getter
@Entity
@Table(name = "vinculacion")
public class Vinculacion extends AccesoAHeladeras{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_en_situacion_vulnerabre", referencedColumnName = "id_persona_en_situacion_vulnerable")
    private PersonaSituacionVulnerable personaSituacionVulnerable;
    @ManyToOne
    @JoinColumn(name = "colaborador_registrante", referencedColumnName = "id_colaborador")
    private Colaborador colaboradorQueRegistro;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @Column(name = "usos_restantes_por_dia")
    private int cantUsosRestantesPorDia;
    @Column(name = "fecha_ultimo_uso")
    private LocalDate fechaUltimoUso;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER )
    @JoinColumn(name = "acceso_a_heladeras", referencedColumnName = "codigo_tarjeta")
    private List <Apertura> aperturasDeHeladera;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER )
    @JoinColumn(name = "consumidor_final", referencedColumnName = "codigo_tarjeta")
    private List <Vianda> viandasRetiradas;

    // Constructores -----------------------------------------------------------
    public Vinculacion(String codigoTarjeta,
                       Colaborador colaboradorQueRegistro,
                       PersonaSituacionVulnerable personaSituacionVulnerable) {
        this.codigoTarjeta = codigoTarjeta;
        this.personaSituacionVulnerable = personaSituacionVulnerable;
        this.colaboradorQueRegistro = colaboradorQueRegistro;
        reiniciarUsosPermitidos();
        this.fechaRegistro = LocalDate.now();
        this.fechaUltimoUso = LocalDate.now();
        this.viandasRetiradas = new ArrayList<>();
        this.aperturasDeHeladera = new ArrayList<>();
    }

    public Vinculacion() {

    }
    // Metodos  -----------------------------------------------------------------------------------------------------
    @Override
    public boolean estaAutorizadaLaApertura(Heladera heladera) {
        consultarUltimoAcceso();
        if(cantUsosRestantesPorDia > 0) {
            log.info("La tarjeta {} tiene autorizada la apertura porque cuenta con {} usos restantes", codigoTarjeta, cantUsosRestantesPorDia);
            registrarAcceso(heladera);
            return true;
        } else
        {
            log.info("La tarjeta {} no tiene autorizada la apertura, no le quedan usos restantes por hoy", codigoTarjeta);
            return false;
        }
    }

    protected void registrarAcceso(Heladera heladera) {
        Vianda viandaRetirada = heladera.retirarViandas(1).getFirst();
        viandaRetirada.consumir();
        viandasRetiradas.add(viandaRetirada);
        log.info("Se retiró para consumo la vianda: {} de la {} ID:{}", viandaRetirada.getTipoDeComida(), heladera.getNombreDelPunto(), heladera.getid_heladera());

        Apertura nuevaApertura = new Apertura(heladera, RETIRAR_VIANDA);
        nuevaApertura.setFechaApertura(LocalDateTime.now());
        nuevaApertura.setCantidadViandasInvolucradas(1);
        aperturasDeHeladera.add(nuevaApertura);

        log.info("Se relizó una apertura de la {} ID:{} con la tarjeta {} con el motivo RETIRAR_VIANDA",
                heladera.getNombreDelPunto(), heladera.getid_heladera(), codigoTarjeta);
        cantUsosRestantesPorDia--;
        log. info("A la tarjeta {} le quedan {} usos restantes por el día de hoy", codigoTarjeta, cantUsosRestantesPorDia);
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
}
