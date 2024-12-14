package Repositories.heladera;

import DAOs.FallasPorHeladeraDAO;
import DAOs.ViandasPorColaboradorDAO;
import DAOs.ViandasPorHeladeraDAO;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.reportes.FallasPorHeladera;
import Modelo.Dominio.reportes.ViandasPorHeladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HeladeraRepository extends JpaRepository<Heladera, Integer> {

    @Query(value =
                    "select new DAOs.FallasPorHeladeraDAO( h.id_heladera, cast(count(i.id_incidente) as int ) ) " +
                    "from Heladera h join Incidente i on i.heladeraDondeOcurrio.id_heladera = h.id_heladera " +
                    "join FallaTecnica ft on ft.id_incidente = i.id_incidente " +
                    "where i.momentoDelSuceso >= ?1 and i.momentoDelSuceso <= ?2 " +
                    "group by h.id_heladera"

    )
    List<FallasPorHeladeraDAO> traerReportesDeFallasEntreFechas(LocalDateTime fecha_inicio, LocalDateTime fecha_fin);

    @Query(
            value = "select h " +
                    "from Heladera h " +
                    "where h.estado != 'DADA_DE_BAJA'"
    )
    List<Heladera> traerHeladerasActivasEnElSistema();

    @Query(
            value = "select h " +
                    "from Heladera h " +
                    "where h.colaboradorACargo.id_colaborador = ?1 and h.estado != 'DADA_DE_BAJA'"
    )
    List<Heladera> traerHeladerasDeUnColaborador(Integer id_colaborador);

    @Query(
            value = "select h " +
                    "from Heladera h " +
                    "   join Alerta a on h.id_heladera=a.heladeraDondeOcurrio.id_heladera " +
                    "where h.colaboradorACargo.id_colaborador = ?1 and h.estado != 'DADA_DE_BAJA'"
    )
    List<Heladera> traerHeladerasDeUnColaboradorConAlertas(Integer id_colaborador);

    @Query(
            value = "select h " +
                    "from Heladera h " +
                    "where h.colaboradorACargo.id_colaborador = ?1 and h.estado = 'DADA_DE_BAJA'"
    )
    List<Heladera> traerHeladerasDadasDeBajaDeUnColaborador(Integer id_colaborador);

}
