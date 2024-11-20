package Modelo.Dominio.Repositories;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.persona.PersonaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Integer> {}
