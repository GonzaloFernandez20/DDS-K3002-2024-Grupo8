package Modelo.Mappers;

import DTOs.FallaTecnicaDTO;
import Modelo.Dominio.incidentes.FallaTecnica;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FactoryFallaTecnica {
    public static FallaTecnica CrearFallaTecnicaAPartirDe(FallaTecnicaDTO dto) {
        MultipartFile file = dto.getFoto();
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String path = resultStringBuilder.toString();

        FallaTecnica nuevaFallaTecnica = new FallaTecnica(
          dto.getColaboradorInformante(),
          dto.getDescripcion(),
          dto.getHeladera(),
          path
        );
        return nuevaFallaTecnica;
    }
}
