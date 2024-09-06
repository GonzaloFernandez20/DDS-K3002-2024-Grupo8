package Modelo.Dominio.GestionDeContribuciones;

import DTOs.DonacionDeDineroDTO;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Factorys.FactoryDonacionDeDinero;

public class GestorDonacionDeDinero {
    public static void crearContribucion(DonacionDeDineroDTO donacionDTO){
        DonacionDeDinero nuevaDonacion = FactoryDonacionDeDinero.crearContribucionAPartirDe(donacionDTO);
        nuevaDonacion.procesarLaContribucion();
        // NEXT: MENSAJE RELACIONADO CON LA PERSISTENCIA
    }
}
