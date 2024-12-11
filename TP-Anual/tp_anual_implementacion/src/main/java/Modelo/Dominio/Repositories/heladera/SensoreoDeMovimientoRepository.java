package Modelo.Dominio.Repositories.heladera;

import Modelo.Dominio.heladera.SensoreoDeMovimiento;
import Modelo.Dominio.heladera.SensoreoDeTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensoreoDeMovimientoRepository extends JpaRepository<SensoreoDeMovimiento, Integer> {

    @Query(value = "SELECT * FROM sensores_de_movimiento WHERE heladera_asociada = ?1", nativeQuery = true)
    Optional <SensoreoDeMovimiento> obtenerSensorDeHeladera(String id_sensor);
}
