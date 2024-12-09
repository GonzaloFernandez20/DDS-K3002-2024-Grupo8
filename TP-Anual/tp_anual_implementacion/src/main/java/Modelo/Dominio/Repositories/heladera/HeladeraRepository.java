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
                    "where h.colaboradorACargo.id_colaborador = ?1"
    )
    List<Heladera> traerHeladerasDeUnColaborador(Integer id_colaborador);

    @Query(
            value = "select h " +
                    "from Heladera h " +
                    "   join Alerta a on h.id_heladera=a.heladeraDondeOcurrio.id_heladera " +
                    "where h.colaboradorACargo.id_colaborador = ?1"
    )
    List<Heladera> traerHeladerasDeUnColaboradorConAlertas(Integer id_colaborador);

    @Modifying
    @Query("DELETE FROM Vianda v WHERE v.heladera.id_heladera = ?1")
    void eliminarViandasPorHeladera(Integer idHeladera);

    @Modifying
    @Transactional
    @Query("DELETE FROM Heladera h WHERE h.id_heladera = ?1")
    void eliminarHeladeraPorId(Integer idHeladera);

    @Modifying
    @Query(
            "UPDATE HacerseCargoDeHeladera h " +
                    "SET h.heladeraACargo = NULL " +
                    "WHERE h.heladeraACargo.id_heladera = ?1"
    )
    void desvincularHeladeraDeHacerseCargo(Integer idHeladera);

    @Modifying
    @Query(
            "UPDATE ContribucionConApertura c " +
                    "SET c.heladeraDestino = NULL " +
                    "WHERE c.heladeraDestino.id_heladera = ?1"
    )
    void desvincularHeladeraDeContribucionConApertura(Integer idHeladera);

    @Modifying
    @Query(
            "UPDATE DistribucionDeViandas d " +
                    "SET d.heladeraDeOrigen = NULL " +
                    "WHERE d.heladeraDeOrigen.id_heladera = ?1"
    )
    void desvincularHeladeraDistribucionViandas(Integer idHeladera);

    @Transactional
    default void eliminarDependenciasDeLaHeladera(Integer idHeladera) {
        desvincularHeladeraDeHacerseCargo(idHeladera);
        desvincularHeladeraDeContribucionConApertura(idHeladera);
        desvincularHeladeraDistribucionViandas(idHeladera);
        eliminarViandasPorHeladera(idHeladera);
    }
}
