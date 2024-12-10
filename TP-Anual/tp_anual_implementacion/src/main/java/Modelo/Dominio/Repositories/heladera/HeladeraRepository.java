package Modelo.Dominio.Repositories.heladera;

import Modelo.Dominio.heladera.Heladera;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HeladeraRepository extends JpaRepository<Heladera, Integer> {
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
