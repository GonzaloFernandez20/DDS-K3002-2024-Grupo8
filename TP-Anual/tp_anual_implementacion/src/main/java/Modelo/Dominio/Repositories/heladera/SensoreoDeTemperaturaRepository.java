package Modelo.Dominio.Repositories.heladera;

import Modelo.Dominio.heladera.SensoreoDeTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensoreoDeTemperaturaRepository extends JpaRepository<SensoreoDeTemperatura, Integer> {

    @Query(value = "SELECT * FROM sensores_de_temperatura WHERE heladera_asociada = ?1", nativeQuery = true)
    Optional <SensoreoDeTemperatura> obtenerSensorDeHeladera(String id_sensor);
}
