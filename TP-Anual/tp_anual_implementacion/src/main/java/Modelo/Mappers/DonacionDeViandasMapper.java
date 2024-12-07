package Modelo.Mappers;

import DTOs.DonacionDeViandaDTO;
import DTOs.ViandaDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.DonacionDeViandas;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.heladera.Heladera;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonacionDeViandasMapper {

    public static DonacionDeViandas crearDonacionDeViandasAPartirDe(DonacionDeViandaDTO dto, Heladera destino, Colaborador colaborador){
        DonacionDeViandas nuevaDonacion = new DonacionDeViandas(colaborador,
                                                              destino,
                                                              crearListaViandas(dto.getViandasDTO(), destino, colaborador),
                                                              LocalDate.now());
        return nuevaDonacion;
    }
    private static List<Vianda> crearListaViandas(List<ViandaDTO> viandas, Heladera destino, Colaborador colaborador){
        List<Vianda> viandasDonadas = new ArrayList<>();

        for(ViandaDTO vianda : viandas){
            Vianda nuevaVianda = new Vianda(
                    vianda.getTipoDeComida(),
                    vianda.getFechaDeCaducidad(),
                    colaborador,
                    destino,
                    vianda.getCalorias(),
                    vianda.getPeso() );
            viandasDonadas.add(nuevaVianda);
        }
        return viandasDonadas;
    }
}
