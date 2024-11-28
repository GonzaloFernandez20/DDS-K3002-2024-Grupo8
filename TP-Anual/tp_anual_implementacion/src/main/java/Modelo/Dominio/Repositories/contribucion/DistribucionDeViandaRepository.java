package Modelo.Dominio.Repositories.contribucion;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.DistribucionDeVianda;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistribucionDeViandaRepository extends JpaRepository<DistribucionDeVianda, Integer> {}
