package Modelo.Dominio.contribucion;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "RegistroDePersonasEnSituacionVulnerable")
public class RegistroDePersonaVulnerable extends Contribucion {
     @OneToOne
     @JoinColumn(name = "vinculacion", referencedColumnName = "codigo_tarjeta")
     private Vinculacion tarjetaEntregada;

    //Construcctores ---------------------------------------------------------------------------------------------------------------------
    public RegistroDePersonaVulnerable(Colaborador colaborador, Vinculacion tarjetaEntregada, LocalDate fechaDeContribucion) {
        this.tarjetaEntregada = tarjetaEntregada;
        this.colaborador = colaborador;
        this.fechaDeContribucion = fechaDeContribucion;
    }

    public RegistroDePersonaVulnerable() {

    }

    //Metodos -------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void procesarLaContribucion() {
        colaborador.registrarContribucion(this);

    }

    @Override
    public double puntosQueSumaColaborador() {
        return 2;
    }
}
