package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.RegistroDeRecaudacion;
import Modelo.Dominio.sistema.Sistema;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "Contribucion")
public class DonacionDeDinero extends Contribucion {
    @Column(name = "monto")
    private final float monto;
    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    public DonacionDeDinero(Colaborador colaborador, float monto, Frecuencia frecuencia, LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.frecuencia = frecuencia;
        this.fechaDeContribucion = fechaDeContribucion;
    }

    @Override
    public void procesarLaContribucion() {
        RegistroDeRecaudacion.getInstancia().recibirDinero(monto);
        colaborador.registrarContribucion(this);
    }
    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 0.5;
        return monto * coeficiente;
    }
}