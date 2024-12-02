package Modelo.Dominio.Repositories.heladera;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.reportes.FallasPorHeladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface HeladeraRepository extends JpaRepository<Heladera, Integer> {

    @Query(value =
            "SELECT h.id_heladera, COUNT(DISTINCT i.id_incidente) " +
            "FROM heladera h LEFT JOIN incidente i ON(h.id_heladera = i.heladera_donde_ocurrio) " +
            "JOIN falla_tecnica f ON(f.id_incidente = i.id_incidente) " +
            "WHERE i.momento_del_suceso BETWEEN ?1 AND ?2 " +
            "GROUP BY h.id_heladera"
            , nativeQuery = true)
    Map<Integer, Integer> traerReportesDeFallasEntreFechas(LocalDate fecha_inicio, LocalDate fecha_fin);
}
