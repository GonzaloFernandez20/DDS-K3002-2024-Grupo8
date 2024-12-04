package Repositories.Accesos_a_heladeras;

import DAOs.ViandasPorHeladeraDAO;
import Modelo.Dominio.Accesos_a_heladeras.Apertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AperturaRepository extends JpaRepository<Apertura, Integer> {
    @Query(
            value = "select new DAOs.ViandasPorHeladeraDAO(h.id_heladera, cast(sum(a1.cantidadViandasInvolucradas) as int), cast(sum(a2.cantidadViandasInvolucradas) as int)) " +
                    "from Heladera h join Apertura a1 on a1.heladera.id_heladera = h.id_heladera " +
                    "join Apertura a2 on a2.heladera.id_heladera = h.id_heladera " +
                    "where a1.fechaApertura <= ?1 and ?2 <= a1.fechaApertura " +
                    "and a2.fechaApertura <= ?1 and ?2 <= a2.fechaApertura " +
/*FALLA EN RECONOCER EL ENUM DEL MOTIVO                    "and (a1.motivo = MotivoApertura.RETIRAR_VIANDA or a1.motivo = MotivoApertura.TRASLADAR_VIANDAS) " +
                    "and (a2.motivo = MotivoApertura.INGRESAR_VIANDAS_TRASLADADAS or a2.motivo = MotivoApertura.INGRESAR_VIANDAS_DONADAS ) " +
*/                    "group by h.id_heladera"
    )
    List<ViandasPorHeladeraDAO> traerViandasIngresadasYRetiradasEntreFechas(LocalDateTime fecha_inicio, LocalDateTime fecha_fin);
}
