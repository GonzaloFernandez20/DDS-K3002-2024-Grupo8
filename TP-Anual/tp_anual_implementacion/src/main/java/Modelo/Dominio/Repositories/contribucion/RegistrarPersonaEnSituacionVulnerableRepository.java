package Modelo.Dominio.Repositories.contribucion;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.RegistroDePersonasEnSituacionVulnerable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrarPersonaEnSituacionVulnerableRepository extends JpaRepository<RegistroDePersonasEnSituacionVulnerable, Integer> {}
