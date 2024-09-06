package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Frecuencia;

import java.time.LocalDate;

public class DonacionDeDineroDTO {
    Colaborador colaborador;
    float monto;
    Frecuencia frecuencia;

    public DonacionDeDineroDTO(Colaborador colaborador, float monto, Frecuencia frecuencia) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.frecuencia = frecuencia;
    }


    public Colaborador getColaborador() { return colaborador; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }
    public float getMonto() { return monto; }
    public void setMonto(float monto) { this.monto = monto; }
    public Frecuencia getFrecuencia() { return frecuencia; }
    public void setFrecuencia(Frecuencia frecuencia) { this.frecuencia = frecuencia; }
}
