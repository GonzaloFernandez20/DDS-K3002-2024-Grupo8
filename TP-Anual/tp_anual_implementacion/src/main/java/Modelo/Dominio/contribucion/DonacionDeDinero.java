package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.RegistroDeRecaudacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "donacion_de_dinero")
public class DonacionDeDinero extends Contribucion {
    @Column(name = "monto")
    private float monto;
    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    //Constructores ------------------------------------------------------------------------------------------------------
    public DonacionDeDinero(Colaborador colaborador, float monto, Frecuencia frecuencia, LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.frecuencia = frecuencia;
        this.fechaDeContribucion = fechaDeContribucion;
    }
    public DonacionDeDinero() {}

    //Metodos ----------------------------------------------------------------
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