package Modelo.Dominio.Repositories;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ViandaRepository extends JpaRepository<Vianda, Integer> {}
