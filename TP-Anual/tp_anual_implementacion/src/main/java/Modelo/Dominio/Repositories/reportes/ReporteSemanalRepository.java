package Modelo.Dominio.Repositories.reportes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.reportes.ReporteSemanal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReporteSemanalRepository extends JpaRepository<ReporteSemanal, Integer> {}
