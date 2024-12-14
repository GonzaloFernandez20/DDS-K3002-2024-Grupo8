package Repositories.Accesos_a_heladeras;

import DAOs.ViandasPorHeladeraDAO;
import Modelo.Dominio.Accesos_a_heladeras.Apertura;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.*;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface AperturaRepository extends JpaRepository<Apertura, Integer> {
    /*@Query(
            value = "select new DAOs.ViandasPorHeladeraDAO( aux_retiradas.ID_HELADERA, aux_retiradas.CANTIDAD_RETIRADAS, aux_ingresadas.CANTIDAD_INGRESADAS ) " +
                    "from " +
                    "( select h.id_heladera as ID_HELADERA, cast(sum(a1.cantidadViandasInvolucradas) as int) as CANTIDAD_RETIRADAS " +
                    "from Heladera h join Apertura a1 on a1.heladera.id_heladera = h.id_heladera " +
                    "where a1.fechaApertura >= ?1 and ?2 >= a1.fechaApertura " +
                    "and ( a1.motivo = MotivoApertura.RETIRAR_VIANDA or a1.motivo = MotivoApertura.TRASLADAR_VIANDAS ) " +
                    "group by h.id_heladera ) as aux_retiradas " +
                    "full join " +
                    "(select h.id_heladera as ID_HELADERA, cast(sum(a2.cantidadViandasInvolucradas) as int) as CANTIDAD_INGRESADAS " +
                    "from Heladera h join Apertura a2 on a2.heladera.id_heladera = h.id_heladera " +
                    "where a2.fechaApertura >= ?1 and ?2 >= a2.fechaApertura " +
                    "and (a2.motivo = MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS or a2.motivo = MotivoApertura.INGRESAR_VIANDAS_DONADAS ) " +
                    "group by h.id_heladera) as aux_ingresadas " +
                    "on aux_retiradas.ID_HELADERA = aux_ingresadas.ID_HELADERA "
    )*/

    /*@Query(
            value = "SELECT new DAOs.ViandasPorHeladeraDAO( " +
                    "    h.id_heladera, " +
                    "    SUM(CASE WHEN a.motivo = MotivoApertura.RETIRAR_VIANDA OR a.motivo = MotivoApertura.TRASLADAR_VIANDAS " +
                    "        THEN a.cantidadViandasInvolucradas ELSE 0 END), " +
                    "    SUM(CASE WHEN a.motivo = MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS OR a.motivo = MotivoApertura.INGRESAR_VIANDAS_DONADAS " +
                    "        THEN a.cantidadViandasInvolucradas ELSE 0 END) " +
                    ") " +
                    "FROM Heladera h " +
                    "LEFT JOIN Apertura a on h.id_heladera = a.heladera.id_heladera " +
                    "WHERE a.fechaApertura >= ?1 and ?2 >= a.fechaApertura " +
                    "GROUP BY h.id_heladera"
    )
    List<ViandasPorHeladeraDAO> traerViandasIngresadasYRetiradasEntreFechas(LocalDateTime fecha_inicio, LocalDateTime fecha_fin);
    */
    @Query(
            value = "select " +
                    "sum(a.cantidadViandasInvolucradas) " +
                    "from Apertura a " +
                    "where a.heladera.id_heladera = ?1 and " +
                    "a.fechaApertura between ?2 and ?3 " +
                    "and (a.motivo = MotivoApertura.RETIRAR_VIANDA or a.motivo = MotivoApertura.TRASLADAR_VIANDAS) " +
                    "group by a.heladera.id_heladera"
    )
    Integer traerViandasRetiradasEntreFechasDeUnaHeladera(Integer id_heladera, LocalDateTime fecha_inicio, LocalDateTime fecha_fin);

    @Query(
            value = "select sum(a.cantidadViandasInvolucradas) " +
                    "from Apertura a " +
                    "where a.heladera.id_heladera = ?1 and " +
                    "a.fechaApertura between ?2 and ?3 " +
                    "and (a.motivo = MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS OR a.motivo = MotivoApertura.INGRESAR_VIANDAS_DONADAS) " +
                    "group by a.heladera.id_heladera"
    )
    Integer traerViandasIngresadasEntreFechasDeUnaHeladera(Integer id_heladera, LocalDateTime fecha_inicio, LocalDateTime fecha_fin);

    // Cuando no tiene se guarda en null. Es repetitivo? Sí, pero así funciona, seguro hay otra forma de hacerlo mejor pero no me quedó tiempo.
}
