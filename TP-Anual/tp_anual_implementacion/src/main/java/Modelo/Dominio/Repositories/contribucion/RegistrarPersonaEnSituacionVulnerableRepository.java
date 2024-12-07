package Modelo.Dominio.Repositories.contribucion;

import Modelo.Dominio.contribucion.RegistroDePersonaVulnerable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrarPersonaEnSituacionVulnerableRepository extends JpaRepository<RegistroDePersonaVulnerable, Integer> {}
