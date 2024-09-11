package Modelo.Factorys;

import DTOs.DonacionDeViandaDTO;
import DTOs.ViandaDTO;
import Modelo.Dominio.contribucion.DonacionDeVianda;
import Modelo.Dominio.contribucion.Vianda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BuilderDonacionDeViandas {

    public static DonacionDeVianda crearDonacionAPartirDe(DonacionDeViandaDTO donacionDeViandaDTO) {
        DonacionDeVianda nuevaDonacion = new DonacionDeVianda(
                donacionDeViandaDTO.getColaborador(),
                donacionDeViandaDTO.getHeladera(),
                crearListaViandas(donacionDeViandaDTO.getViandasDTO()),
                LocalDate.now());
        return nuevaDonacion;
    }

    private static List<Vianda> crearListaViandas(List<ViandaDTO> viandas){
        List<Vianda> viandasDonadas = new ArrayList<>();

        for(ViandaDTO vianda : viandas){
            Vianda nuevaVianda = new Vianda(
                    vianda.getTipoDeComida(),
                    vianda.getFechaDeCaducidad(),
                    vianda.getColaborador(),
                    vianda.getHeladera(),
                    vianda.getCalorias(),
                    vianda.getPeso() );
            viandasDonadas.add(nuevaVianda);
        }
        return viandasDonadas;
    }
}
