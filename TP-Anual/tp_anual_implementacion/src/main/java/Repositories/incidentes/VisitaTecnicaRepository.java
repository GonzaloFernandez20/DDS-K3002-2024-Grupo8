package Repositories.incidentes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.incidentes.VisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica, Integer> {}
