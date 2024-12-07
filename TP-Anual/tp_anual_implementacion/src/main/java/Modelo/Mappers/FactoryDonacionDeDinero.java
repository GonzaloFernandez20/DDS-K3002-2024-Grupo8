package Modelo.Mappers;

import DTOs.DonacionDeDineroDTO;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.contribucion.Frecuencia;
import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FactoryDonacionDeDinero {

    private static GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public FactoryDonacionDeDinero(GestorInicioDeSesion gestorInicioDeSesion) {
        FactoryDonacionDeDinero.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    public static DonacionDeDinero crearContribucionAPartirDe(DonacionDeDineroDTO donacionDeDineroDTO){
        Frecuencia frecuencia = Frecuencia.valueOf(donacionDeDineroDTO.getFrecuencia());

        return new DonacionDeDinero(
                gestorInicioDeSesion.obtenerColaboradorPorID(),
                donacionDeDineroDTO.getMonto(),
                frecuencia,
                LocalDate.now());
    }
}
