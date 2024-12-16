package Modelo.Mappers;

import DTOs.DonacionDeDineroDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.contribucion.Frecuencia;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DonacionDeDineroMapper {

    public static DonacionDeDinero crearContribucionAPartirDe(DonacionDeDineroDTO donacionDeDineroDTO, Colaborador colaborador){
        Frecuencia frecuencia = Frecuencia.valueOf(donacionDeDineroDTO.getFrecuencia());

        return new DonacionDeDinero(
                colaborador,
                donacionDeDineroDTO.getMonto(),
                frecuencia,
                LocalDate.now());
    }
}
