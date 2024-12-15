package Repositories.contribucion;

import Modelo.Dominio.contribucion.RegistroDePersonaVulnerable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RegistrarPersonaEnSituacionVulnerableRepository extends JpaRepository<RegistroDePersonaVulnerable, Integer> {}
