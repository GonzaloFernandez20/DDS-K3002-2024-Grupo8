package Modelo.Dominio.Repositories.persona;

import Modelo.Dominio.Persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends JpaRepository<Persona, Integer> {}
