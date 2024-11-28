package Modelo.Dominio.Repositories.persona;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends JpaRepository<Persona, Integer> {}
