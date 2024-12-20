package Modelo.Mappers;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.FallaTecnica;
import Utils.DescargaDeArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class FactoryFallaTecnica {
    public static FallaTecnica CrearFallaTecnicaAPartirDe(FallaTecnicaDTO dto) {
        // Guardar la foto
        DescargaDeArchivo.guardarArchivo("img/fotosHeladerasReportadas/", dto.getFoto());
        FallaTecnica nuevaFallaTecnica = new FallaTecnica(
          dto.getColaboradorInformante(),
          dto.getDescripcion(),
          dto.getHeladera(),
          "img/fotosHeladerasReportadas/" + dto.getFoto()
        );
        return nuevaFallaTecnica;
    }

}
