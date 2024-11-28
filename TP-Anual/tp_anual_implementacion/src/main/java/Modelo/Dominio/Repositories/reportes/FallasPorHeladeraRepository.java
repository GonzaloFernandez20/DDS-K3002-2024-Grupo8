package Modelo.Dominio.Repositories.reportes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.reportes.FallasPorHeladera;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FallasPorHeladeraRepository extends JpaRepository<FallasPorHeladera, Integer> {}
