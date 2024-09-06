package Modelo.Factorys;

import DTOs.DonacionDeDineroDTO;
import Modelo.Dominio.contribucion.DonacionDeDinero;

public class FactoryDonacionDeDinero {
    public static DonacionDeDinero crearContribucionAPartirDe(DonacionDeDineroDTO donacionDeDineroDTO){
        DonacionDeDinero nuevaDonacion = new DonacionDeDinero(donacionDeDineroDTO.getColaborador(),
                donacionDeDineroDTO.getMonto(),
                donacionDeDineroDTO.getFrecuencia());
        return nuevaDonacion;
    }
}
