package Repositories.incidentes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.incidentes.FallaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FallaTecnicaRepository extends JpaRepository<FallaTecnica, Integer> {}
