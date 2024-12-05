package Repositories.Accesos_a_heladeras;

import DAOs.ViandasPorHeladeraDAO;
import Modelo.Dominio.Accesos_a_heladeras.Apertura;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AperturaRepository extends JpaRepository<Apertura, Integer> {
    @Query(
            value = "select new DAOs.ViandasPorHeladeraDAO( " +
                    "    COALESCE(retiradas.ID_HELADERA, ingresadas.ID_HELADERA), " +
                    "    COALESCE(retiradas.CANTIDAD_RETIRADAS, 0), " +
                    "    COALESCE(ingresadas.CANTIDAD_INGRESADAS, 0) " +
                    ") " +
                    "from " +
                    "( " +
                    "    select h.id_heladera as ID_HELADERA, " +
                    "           cast(sum(a1.cantidadViandasInvolucradas) as int) as CANTIDAD_RETIRADAS " +
                    "    from Heladera h " +
                    "    join Apertura a1 on a1.heladera.id_heladera = h.id_heladera " +
                    "    where a1.fechaApertura >= ?1 and ?2 >= a1.fechaApertura " +
                    "    and (a1.motivo = MotivoApertura.RETIRAR_VIANDA or a1.motivo = MotivoApertura.TRASLADAR_VIANDAS) " +
                    "    group by h.id_heladera " +
                    ") retiradas " +
                    "left join " +
                    "( " +
                    "    select h.id_heladera as ID_HELADERA, " +
                    "           cast(sum(a2.cantidadViandasInvolucradas) as int) as CANTIDAD_INGRESADAS " +
                    "    from Heladera h " +
                    "    join Apertura a2 on a2.heladera.id_heladera = h.id_heladera " +
                    "    where a2.fechaApertura >= ?1 and ?2 >= a2.fechaApertura " +
                    "    and (a2.motivo = MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS or a2.motivo = MotivoApertura.INGRESAR_VIANDAS_DONADAS) " +
                    "    group by h.id_heladera " +
                    ") ingresadas " +
                    "on retiradas.ID_HELADERA = ingresadas.ID_HELADERA " +
                    "union all " +
                    "select new DAOs.ViandasPorHeladeraDAO( " +
                    "    COALESCE(retiradas.ID_HELADERA, ingresadas.ID_HELADERA), " +
                    "    COALESCE(retiradas.CANTIDAD_RETIRADAS, 0), " +
                    "    COALESCE(ingresadas.CANTIDAD_INGRESADAS, 0) " +
                    ") " +
                    "from " +
                    "( " +
                    "    select h.id_heladera as ID_HELADERA, " +
                    "           cast(sum(a1.cantidadViandasInvolucradas) as int) as CANTIDAD_RETIRADAS " +
                    "    from Heladera h " +
                    "    join Apertura a1 on a1.heladera.id_heladera = h.id_heladera " +
                    "    where a1.fechaApertura >= ?1 and ?2 >= a1.fechaApertura " +
                    "    and (a1.motivo = MotivoApertura.RETIRAR_VIANDA or a1.motivo = MotivoApertura.TRASLADAR_VIANDAS) " +
                    "    group by h.id_heladera " +
                    ") retiradas " +
                    "right join " +
                    "( " +
                    "    select h.id_heladera as ID_HELADERA, " +
                    "           cast(sum(a2.cantidadViandasInvolucradas) as int) as CANTIDAD_INGRESADAS " +
                    "    from Heladera h " +
                    "    join Apertura a2 on a2.heladera.id_heladera = h.id_heladera " +
                    "    where a2.fechaApertura >= ?1 and ?2 >= a2.fechaApertura " +
                    "    and (a2.motivo = MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS or a2.motivo = MotivoApertura.INGRESAR_VIANDAS_DONADAS) " +
                    "    group by h.id_heladera " +
                    ") ingresadas " +
                    "on retiradas.ID_HELADERA = ingresadas.ID_HELADERA"
    )
    List<ViandasPorHeladeraDAO> traerViandasIngresadasYRetiradasEntreFechas(LocalDateTime fecha_inicio, LocalDateTime fecha_fin);
}
