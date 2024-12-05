package DTOs;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Frecuencia;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DonacionDeDineroDTO {
    float monto;
    String frecuencia;

    public DonacionDeDineroDTO(float monto, String frecuencia) {
        this.monto = monto;
        this.frecuencia = frecuencia;
    }
}
