package Repositories.tecnico;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.tecnico.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {}
