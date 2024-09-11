package Modelo.Factorys;

import DTOs.DonacionDeDineroDTO;
import Modelo.Dominio.contribucion.DonacionDeDinero;

import java.time.LocalDate;

public class FactoryDonacionDeDinero {
    public static DonacionDeDinero crearContribucionAPartirDe(DonacionDeDineroDTO donacionDeDineroDTO){
        DonacionDeDinero nuevaDonacion = new DonacionDeDinero(donacionDeDineroDTO.getColaborador(),
                donacionDeDineroDTO.getMonto(),
                donacionDeDineroDTO.getFrecuencia(),
                LocalDate.now());
        return nuevaDonacion;
    }
}
