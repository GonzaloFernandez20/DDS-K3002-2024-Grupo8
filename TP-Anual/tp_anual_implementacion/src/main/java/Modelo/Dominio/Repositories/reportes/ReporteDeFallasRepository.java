package Modelo.Dominio.Repositories.reportes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.reportes.ReporteDeFallas;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReporteDeFallasRepository extends JpaRepository<ReporteDeFallas, Integer> {}
