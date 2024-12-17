package Repositories.Accesos_a_heladeras;

import DAOs.ViandasPorHeladeraDAO;
import Modelo.Dominio.Accesos_a_heladeras.Apertura;
import Modelo.Dominio.Accesos_a_heladeras.MotivoApertura.*;
import Modelo.Dominio.heladera.Heladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface AperturaRepository extends JpaRepository<Apertura, Integer> {

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
