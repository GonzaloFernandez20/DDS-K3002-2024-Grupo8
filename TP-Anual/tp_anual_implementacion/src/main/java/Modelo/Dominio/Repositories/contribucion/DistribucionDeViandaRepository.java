package Modelo.Dominio.Repositories.contribucion;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.DistribucionDeViandas;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistribucionDeViandaRepository extends JpaRepository<DistribucionDeViandas, Integer> {}
