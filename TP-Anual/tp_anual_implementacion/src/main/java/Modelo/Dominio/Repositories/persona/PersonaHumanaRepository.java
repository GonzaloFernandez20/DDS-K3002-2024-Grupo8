package Modelo.Dominio.Repositories.persona;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.persona.PersonaHumana;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaHumanaRepository extends JpaRepository<PersonaHumana, Integer> {}
