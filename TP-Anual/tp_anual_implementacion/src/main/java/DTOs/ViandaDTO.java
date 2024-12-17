package DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ViandaDTO {
    private String tipoDeComida;
    private LocalDate fechaDeCaducidad;
    private String calorias;
    private String peso;

    // Constructores------------------------------------------------
    public ViandaDTO() {
    }

    public ViandaDTO(String tipoDeComida,
                     LocalDate fechaDeCaducidad,
                     String calorias,
                     String peso)
    {
        this.tipoDeComida = tipoDeComida;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.calorias = calorias;
        this.peso = peso;
    }
}

