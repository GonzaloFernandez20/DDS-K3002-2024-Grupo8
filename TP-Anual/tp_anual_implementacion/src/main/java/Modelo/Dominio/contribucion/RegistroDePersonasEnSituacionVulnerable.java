package Modelo.Dominio.contribucion;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


import java.time.LocalDate;
@Entity
@Table(name = "RegistroDePersonasEnSituacionVulnerable")
public class RegistroDePersonasEnSituacionVulnerable extends Contribucion {
    @OneToOne
    @JoinColumn(name = "id_vinculacion")
    private Vinculacion tarjetaEntregada;

    public RegistroDePersonasEnSituacionVulnerable(Colaborador colaborador, Vinculacion tarjetaEntregada, LocalDate fechaDeContribucion) {
        this.tarjetaEntregada = tarjetaEntregada;
        this.colaborador = colaborador;
        this.fechaDeContribucion = fechaDeContribucion;
    }

    @Override
    public void procesarLaContribucion() {
        colaborador.registrarContribucion(this);
    }

    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 2;
        return coeficiente;
    }
}
